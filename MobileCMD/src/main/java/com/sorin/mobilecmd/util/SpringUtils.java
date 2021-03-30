package com.sorin.mobilecmd.util;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public final class SpringUtils {
		
	public static <T> T getBean(Class<T> clasz, HttpServletRequest req) {
		return getBean(clasz, req.getSession(true).getServletContext());
	}
	
	/**
	 * Return the bean for the specified class from the ApplicationContext 
	 * found in the specified ServletContext.
	 * @see #getBean(Class, ApplicationContext)
	 */
	public static <T> T getBean(Class<T> clasz, ServletContext servletContext) throws IllegalStateException {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return getBean(clasz, context);
	}
	

	/**
	 * Return the bean from the application context that has the specified type. If more than one bean
	 * exists or no bean can be found an exception is thrown.
	 * @param clasz type of the bean
	 * @param context ApplicationContext to retrieve the bean from.
	 * @return the bean.
	 * @throws IllegalStateException - if the bean can not be found or there is more than one
	 * 				bean matching the type.
	 */
	public static <T> T getBean(Class<T> clasz, ApplicationContext context) throws IllegalStateException {
		Map<String, T> beans = context.getBeansOfType(clasz);
		if (beans.size() != 1) {
			throw new IllegalStateException("Exactly one bean of type " + clasz.getName() + " should be defined. Found " + beans.size() + ".");
		}
		Set<String> keys = beans.keySet();
		T bean = null;
		for (String key:keys) {
			bean = beans.get(key);
		}
		return bean;
	}	
	
	private SpringUtils(){}
}
