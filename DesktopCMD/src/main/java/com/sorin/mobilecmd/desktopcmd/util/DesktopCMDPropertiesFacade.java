package com.sorin.mobilecmd.desktopcmd.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DesktopCMDPropertiesFacade {
	private static final Logger log = Logger.getLogger(DesktopCMDPropertiesFacade.class);
	
	@SuppressWarnings("unused")
	private final static DesktopCMDPropertiesFacade desktopCMDPropertiesFacade = new DesktopCMDPropertiesFacade();
	private static PropertiesFacade propertiesFacade;
	
	
	private DesktopCMDPropertiesFacade() {
		log.debug("DesktopCMDPropertiesFacade - init properties");
		Properties properties = new Properties();
		InputStream in = getClass().getResourceAsStream("/desktopcmd.properties");
		try {
			properties.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		propertiesFacade = new PropertiesFacade(properties);		
		
		// ensure that all required properties are set. Because this bean is instantiated
		// in the application context, the application will not start if there are any 
		// missing properties.
		propertiesFacade.getString("serverPath");
	}
	
	public static String getServerPath() {
		return propertiesFacade.getString("serverPath");
	}
	
	public static URI getServerPathURI() {
		try {
			log.debug("getServerPathURI - getServerPath: " + getServerPath());
			return new URI(getServerPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			log.debug("getServerPathURI - getServerPath: null");
			return null;
		}
	}
	
	public static URI getRegistrationPath() throws URISyntaxException {
		log.debug("getRegistrationPath - getRegistrationPath: " + getServerPath() + "register.htm");
		return new URI(getServerPath() + "register.htm");
	}

	public static String getServerExt() {
		return "." + propertiesFacade.getString("serverPathExtension");
	}
}
