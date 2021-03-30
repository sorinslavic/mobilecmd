package com.sorin.mobilecmd.desktopcmd.ws;

import java.net.URI;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;

public class LoginRequest extends HttpRequestObject {
	private static final Logger log = Logger.getLogger(LoginRequest.class);
	private String username = "";
	private String password = "";
	
	public LoginRequest(String username, char[] password) {
		super("login");
		
		this.username = username;
		for (char c : password)
			this.password += c; 
	}

	@Override
	public HttpMethod getMethod() {
		return HttpMethod.POST;
	}

	@Override
	public URI getRequestURI() throws Exception {
		String req = getRequest() + "?" + "username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
		log.debug("LoginRequest#getRequestURI - " + req);
		return new URI(req);
	}
	
	
	
}
