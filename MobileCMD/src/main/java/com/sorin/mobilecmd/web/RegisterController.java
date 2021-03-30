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

import com.sorin.mobilecmd.data.User;
import com.sorin.mobilecmd.services.UserService;

/**
 * Handles requests for the application registration form.
 */
@Controller
public class RegisterController {	
	private static final Logger log = Logger.getLogger(RegisterController.class);
	
	private final UserService userService;
	
	@Autowired
	public RegisterController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Simply selects the register view to render.
	 */
	@RequestMapping(value = "/register.htm", method = RequestMethod.GET)
	public ModelAndView registerGet(HttpServletRequest request) {
		return new ModelAndView("register");
	}
	
	/**
	 * Try and register the user into the application.
	 * @throws ServletRequestBindingException 
	 */
	@RequestMapping(value="/register.htm", method=RequestMethod.POST)
	public ModelAndView register(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		User user = new User(request);
		model.put("user", user);
		
		if (!user.isValid()) {
			log.debug("register - " + user.getAllErrors());
			return new ModelAndView("register", model);
		}
		
		if (userService.getUserId(user.getUserName()) > 0) {
			log.debug("register - User Name already exists!");
			user.addError("This Username: " + user.getUserName() + " already exists!");
			user.setUserName("");
			return new ModelAndView("register", model);
		}
		
		log.debug("register - user: " + user);
		user = userService.registerUser(user);
		log.debug("succesfully registered user with ID: " + user.getUserID());
		model.put("username", user.getUserName());
		
		model.put("registrationOk", "User " + user.getUserName() + " was successfully created!");
		return new ModelAndView("redirect:home.htm", model);
	}
	
}
