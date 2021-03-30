package com.sorin.mobilecmd.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ErrorController {	
	private static final Logger log = Logger.getLogger(ErrorController.class);
	
	@RequestMapping(value = "/notfound.htm")
	public String notFound(HttpServletRequest request) {
		log.debug("notFound - an invalid path was accessed");
		return "error-404";
	}
	
	@RequestMapping(value = "/error.htm")
	public String error(HttpServletRequest request) {
		log.debug("error - there was an error on the page");
		return "error-exception";
	}
}
