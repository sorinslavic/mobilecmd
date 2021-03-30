package com.sorin.mobilecmd.desktopcmd.ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URI;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import com.sorin.mobilecmd.desktopcmd.util.DesktopCMDPropertiesFacade;

public abstract class HttpRequestObject {
	private static final Logger log = Logger.getLogger(HttpRequestObject.class);
	private static final SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
	
	public final String request;
	private ClientHttpResponse res;
	
	public HttpRequestObject(String request) {
		this.request = request;
		
		log.debug("HttpRequestObject - generating request for: " + request);
	}

	public abstract HttpMethod getMethod();	
	public abstract URI getRequestURI() throws Exception;
	
	public void execute() throws IOException, Exception {
		log.debug("execute - sending request to: " + getRequestURI());
		log.debug("execute - sending request for: " + getMethod());
		ClientHttpRequest req = factory.createRequest(getRequestURI(), getMethod());
		this.res = req.execute();
		
		log.debug("execute - received response stats: " + res.getStatusText());
	}
	
	public ClientHttpResponse getResponse() {
		return res;
	}
	
	public InputStream getResponseStream() throws IOException {
		return res.getBody();
	}
	
	public boolean isOK() throws IOException {
		log.debug("isOK - validating the response" + res.getStatusCode().name());
		if (res.getStatusCode().value() != HttpStatus.OK.value()) 
			return false;
		return true;
	}
	
	public final String getRequest() {
		log.debug("getRequest - the full request is: " + DesktopCMDPropertiesFacade.getServerPath() + request + DesktopCMDPropertiesFacade.getServerExt());
		return DesktopCMDPropertiesFacade.getServerPath() + request + DesktopCMDPropertiesFacade.getServerExt();
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getResponseObject() throws IOException, ClassNotFoundException {

		ObjectInputStream ois = new ObjectInputStream(getResponseStream());
		T t = (T) ois.readObject();
		
		return t;
	}
}
