package com.sorin.mobilecmd.desktopcmd.ws;

import java.net.URI;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;

public class RegisterComputerRequest extends HttpRequestObject {
	private static final Logger log = Logger.getLogger(RegisterComputerRequest.class);

	private String ipAddress;
	private String computerName;
	
	public RegisterComputerRequest(String ipAddress, String computerName) {
		super("registerComputer");
		
		this.ipAddress = ipAddress;
		this.computerName = computerName;
	}

	@Override
	public HttpMethod getMethod() {
		return HttpMethod.POST;
	}

	@Override
	public URI getRequestURI() throws Exception {
		String req = getRequest() + "?";
		req += "ipAddress=" + URLEncoder.encode(ipAddress, "UTF-8") + "&computerName=" + URLEncoder.encode(computerName, "UTF-8");
		req += "&userId=" + URLEncoder.encode(Session.getSession().getUser().getUserID() + "", "UTF-8");
		
		log.debug("RegisterComputerRequest#getRequestURI - " + req);
		return new URI(req);	
	}

}
