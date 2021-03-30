package com.sorin.mobilecmd.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.sorin.mobilecmd.data.User;
import com.sorin.mobilecmd.services.UserService;
import com.sorin.mobilecmd.util.ParameterParser;
import com.sorin.mobilecmd.util.exception.RequiredParameterException;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {	
	private static final Logger log = Logger.getLogger(HomeController.class);
	
	private final UserService userService;
	
	@Autowired
	public HomeController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home.htm", method = RequestMethod.GET)
	public ModelAndView homeGet(HttpServletRequest request) {
		User user = (User) WebUtils.getSessionAttribute(request, "user");
		if (user != null) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("user", user);
			return new ModelAndView("redirect:history.htm", model);
		}
		
		String username = ParameterParser.getStringParameter(request, "username", "");		
		String registrationOk = ParameterParser.getStringParameter(request, "registrationOk", "");
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("username", username);
		model.put("registrationOk", registrationOk);
		return new ModelAndView("home", model);
	}
	
	/**
	 * Try and login into the application.
	 * @throws ServletRequestBindingException 
	 */
	@RequestMapping(value="/home.htm", method=RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request) {
		String password;
		String username = "";
		
		Map<String, Object> model = new HashMap<String, Object>();
				
		try {
			username = ParameterParser.getRequiredStringParameter(request, "username", "User name");
			password = ParameterParser.getRequiredStringParameter(request, "password", "Password");
		} catch (RequiredParameterException e) {
			log.debug("login - " + e.getMessage());
			model.put("username", username);
			model.put("errorParam", e);
			return new ModelAndView("home", model);
		}		
		
		log.debug("login - username: " + username + " / password: " + password);
		
		User user = userService.login(username, password);
		if (user == null) {
			log.debug("login - User Login has failed!");
			model.put("username", username);
			if (userService.getUserId(username) < 0)
				model.put("error", "No such user!");
			else
				model.put("error", "The Password is incorrect!");
			return new ModelAndView("home", model);
		}
		
		WebUtils.setSessionAttribute(request, "user", user);
		model.put("user", user);
		
		return new ModelAndView("redirect:history.htm", model);
	}
}
