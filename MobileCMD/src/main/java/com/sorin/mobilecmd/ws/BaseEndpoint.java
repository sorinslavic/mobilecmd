package com.sorin.mobilecmd.ws;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.oxm.Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.AbstractMarshallingPayloadEndpoint;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;

import com.sorin.mobilecmd.services.WebService;

public abstract class BaseEndpoint extends AbstractMarshallingPayloadEndpoint implements InitializingBean {
	
	private static final Logger log = Logger.getLogger(BaseEndpoint.class);
	
	protected final WebService service;
		
	/**
	 * Constructor
	 * @param marshaller
	 * @throws IOException 
	 */
	public BaseEndpoint(Marshaller marshaller, WebService service) throws IOException {
		super(marshaller);
		this.service = service;
	}
	
	/** @see AbstractMarshallingPayloadEndpoint#onUnmarshalRequest(MessageContext, Object) */
	protected boolean onUnmarshalRequest(MessageContext messageContext,	Object requestObject) throws Exception {
		log.info("onUnmarshalRequest - Received request: " + getMessage(messageContext.getRequest()));
		log.info("onUnmarshalRequest - From: " + getClientIPAddress());		
		return super.onUnmarshalRequest(messageContext, requestObject);
	}
	
	/** @see AbstractMarshallingPayloadEndpoint#onMarshalResponse(MessageContext, Object, object) */
	protected void onMarshalResponse(MessageContext messageContext,	Object requestObject, Object responseObject) {
		log.info("onMarshalResponse - Sending response: " + getMessage(messageContext.getResponse()));
		log.info("onMarshalResponse - To: " + getClientIPAddress());
		super.onMarshalResponse(messageContext, requestObject, responseObject);
	}

	/** @see InitializingBean#afterPropertiesSet() */
	public void afterPropertiesSet() {
		
	}
	
	/**
	 * Return the IP address of the computer that generated the request.
	 * @return
	 */
	protected String getClientIPAddress() {
		TransportContext context = TransportContextHolder.getTransportContext();
		HttpServletConnection connection = (HttpServletConnection )context.getConnection();
		HttpServletRequest request = connection.getHttpServletRequest();
		String ipAddress = request.getRemoteAddr();
		return ipAddress;
	}
	

	/**
	 * Get request and response messages as string.
	 * @param message
	 * @return
	 * @throws IOException
	 */
	private String getMessage(WebServiceMessage message) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			message.writeTo(baos);
		} catch (IOException e) {}		    
		return baos.toString();	    
	}
    			
}
