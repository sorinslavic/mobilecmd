package com.sorin.mobilecmd.desktopcmd.ws;

import java.net.URI;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;

public class ClientFilesRequest extends HttpRequestObject {
	private static final Logger log = Logger.getLogger(ClientFilesRequest.class);

	private int clientId;
	
	public ClientFilesRequest(int clientId) {
		super("getClientFiles");
		
		this.clientId = clientId;
	}

	@Override
	public HttpMethod getMethod() {
		return HttpMethod.GET;
	}

	@Override
	public URI getRequestURI() throws Exception {
		String req = getRequest() + "?" + "clientId=" + URLEncoder.encode(clientId + "", "UTF-8");
		
		log.debug("ClientFilesRequest#getRequestURI - " + req);
		return new URI(req);	
	}

}
