package com.sorin.mobilecmd.util;

import java.util.MissingResourceException;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

public class MobileCMDPropertiesFacade implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	private final PropertiesFacade props;
	private final PasswordRestrictions passwordRestrictions;
	
	public static final String SCHEMA_PACKAGE = "com.sorin.mobilecmd.xml.schema";
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	/**
	 * Constructor that ensures that all required properties exist in mobilecmd.properties,
	 * and creates the password restrictions object.
	 * @param properties
	 * @throws MissingResourceException if a required property is missing.
	 */
	public MobileCMDPropertiesFacade(PropertiesFacade props) throws MissingResourceException {
		Assert.notNull(props, "props");
		this.props = props;		

		// create the password restrictions object
		passwordRestrictions = new PasswordRestrictions(
				props.getInt("PasswordMinLength", -1),
				props.getInt("PasswordDuration", -1),
				props.getBoolean("PasswordReqNumbers"),
				props.getBoolean("PasswordReqSpecial"),
				props.getBoolean("PasswordUpReqLetters")
				);
		
		
		// ensure that all required properties are set. Because this bean is instantiated
		// in the application context, the application will not start if there are any 
		// missing properties.
		props.getRequiredString("sqlURL");
		props.getRequiredString("userName");
		props.getRequiredString("password");
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	/**
	 * @see #MobileCMDPropertiesFacade(PropertiesFacade)
	 */
	public MobileCMDPropertiesFacade(Properties properties) throws MissingResourceException {
		this(new PropertiesFacade(properties));
	}
	
	public PasswordRestrictions getPasswordRestrictions() {
		return passwordRestrictions;
	}
	
	public PropertiesFacade getProperties() {
		return props;
	}

	public String getClientFilePath() {
		return props.getRequiredString("clientFilePath");
	}
}
