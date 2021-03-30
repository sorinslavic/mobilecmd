package com.sorin.mobilecmd.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.sorin.mobilecmd.data.User;

public class UserLoginInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {

	private static final Logger log = Logger.getLogger(UserLoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User user = (User) WebUtils.getSessionAttribute(request, "user");		
		if (user == null) {
			log.info("preHandle - Unauthorized access detected. Redirecting to login page.");
			response.sendRedirect(request.getContextPath() + "/home.htm");			
			return false;
		} else {
			return true;
		}
	}		
}
