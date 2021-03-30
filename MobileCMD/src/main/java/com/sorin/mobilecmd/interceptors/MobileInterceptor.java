package com.sorin.mobilecmd.interceptors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sorin.mobilecmd.util.ParameterParser;

public class MobileInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {
	private static final Logger log = Logger.getLogger(MobileInterceptor.class);
	  
	private List<String> mobileAgents;
	private List<Pattern> uaPatterns;
	
	public void setMobileUserAgents(List<String> agents) {
		mobileAgents = agents;
	}
		
	public void init() {
		uaPatterns = new ArrayList<Pattern>();
	    // Pre-compile the user-agent patterns as specified in servlet-context.xml
		for (String ua : mobileAgents) {
			try {
				uaPatterns.add(Pattern.compile(ua, Pattern.CASE_INSENSITIVE));
			} catch (PatternSyntaxException e) {}
		}
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception {
		String userAgent = request.getHeader("User-Agent");
		log.debug("postHandle - the userAgent we intercepted was: " + userAgent);
		
		if (ParameterParser.getBooleanParameter(request, "fromMobile"))
			return;
		
	    if (userAgent != null) {
	    	if (isMobile(userAgent)) {
	    		model.setViewName("redirect:mobile.htm");
	    	}
	    }
	}
	  
	/**
	 * Returns true of the given User-Agent string matches a suspected mobile device.
	 * @param agent
	 * @return
	 */
	private final boolean isMobile(final String agent) {		
		for (Pattern p : uaPatterns) {
			Matcher m = p.matcher(agent);
			if (m.find()) {
				return true;
			}
		}

		return false;

	}
}
