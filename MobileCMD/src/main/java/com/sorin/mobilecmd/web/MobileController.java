package com.sorin.mobilecmd.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MobileController {
	private static final Logger log = Logger.getLogger(RegisterController.class);
		
	/**
	 * Simply selects the mobile view to render. This request comes for a mobile device
	 */
	@RequestMapping(value = "/mobile.htm", method = RequestMethod.GET)
	public ModelAndView mobileGet(HttpServletRequest request) {
		log.debug("mobileGet - prepare to render mobile view");
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("googlePlayLink", "http://www.google.com");
		
		return new ModelAndView("mobile", model);
	}
}
