package com.sorin.mobilecmd.util;

import javax.servlet.http.HttpServletRequest;

import com.sorin.mobilecmd.util.exception.RequiredParameterException;

public abstract class ParameterParser {

	public static final String getStringParameter(HttpServletRequest req, String param) {
		return req.getParameter(param);
	}
	
	public static final String getStringParameter(HttpServletRequest req, String param, String defaultValue) {
		String ret = getStringParameter(req, param);
		
		if (ret == null)
			return defaultValue;
	
		return ret;				
	}
	
	public static final String getRequiredStringParameter(HttpServletRequest req, String param, String userLabel) throws RequiredParameterException {
		String ret = req.getParameter(param);
		if (ret == null)
			throw new RequiredParameterException(userLabel);
		if (ret.isEmpty())
			throw new RequiredParameterException(userLabel);
		
		return ret;
	}
	
	public static final boolean getBooleanParameter(HttpServletRequest req, String param) {
		String value = req.getParameter(param);
		
		if (value == null)
			return false;
		if (value.isEmpty())
			return false;
		
		if (value.equalsIgnoreCase("true") ||
			value.equalsIgnoreCase("yes") ||
			value.equalsIgnoreCase("on") ||
			value.equals("1"))
			return true;
		
		return false;
	}

	public static int getIntParameter(HttpServletRequest req, String param) {
		String value = req.getParameter(param);
		
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
