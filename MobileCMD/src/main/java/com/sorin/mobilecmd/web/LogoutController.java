package com.sorin.mobilecmd.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
	private static final Logger log = Logger.getLogger(LogoutController.class);
		
	@RequestMapping("/logout.htm")
	public String logout(HttpServletRequest request) {	    
		request.getSession().invalidate();
		log.debug("logout - Session invalidated.");
		return "redirect:/home.htm";
	}
}
