package com.sorin.mobilecmd.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.sorin.mobilecmd.data.Client;
import com.sorin.mobilecmd.data.Device;
import com.sorin.mobilecmd.data.User;
import com.sorin.mobilecmd.services.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HistoryController {	
	private static final Logger log = Logger.getLogger(HistoryController.class);
	
	private final UserService userService;
	
	@Autowired
	public HistoryController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Simply selects the history view to render by returning its name.
	 */
	@RequestMapping(value = "/history.htm", method = RequestMethod.GET)
	public ModelAndView historyGet(HttpServletRequest request) {
		log.debug("historyGet - We have logged in!");
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) WebUtils.getSessionAttribute(request, "user");
		log.debug("historyGet : user is: " + user.getUserName());
		
		model.put("user", user);		
		model.put("downloads", userService.getDownloadCount());
		
		List<Device> deviceList = userService.getDeviceList(user.getUserID());
		List<Client> clientList = userService.getClientList(user.getUserID());
		model.put("deviceList", deviceList);
		model.put("clientList", clientList);
		
		return new ModelAndView("history", model);
	}
}
