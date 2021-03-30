package com.sorin.mobilecmd.ws.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

import com.sorin.mobilecmd.services.WebService;
import com.sorin.mobilecmd.services.WebServiceImpl;
import com.sorin.mobilecmd.util.MobileCMDPropertiesFacade;
import com.sorin.mobilecmd.xml.schema.RunCommand;

@SuppressWarnings("unused")
public class TestClient {
	private static final Logger log = Logger.getLogger(TestClient.class);
	
	public void testLogin() throws Exception {
	/*	JAXBContext jaxbContext = JAXBContext.newInstance(MobileCMDPropertiesFacade.SCHEMA_PACKAGE);
		
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, MobileCMDPropertiesFacade.DEFAULT_ENCODING);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				
		ByteArrayInputStream input = new ByteArrayInputStream ("<n0:LoginRequest xmlns:v=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:c=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:d=\"http://www.w3.org/2001/XMLSchema\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:n0=\"http://www.sorin.com/cmd\" c:root=\"1\" id=\"o0\"><n0:Username>sorin</n0:Username><n0:Password>slavic</n0:Password></n0:LoginRequest>".getBytes()); 
		Object a = unmarshaller.unmarshal(input);
		
		System.out.println(a.getClass());*/
		
		WebService ws = new WebServiceImpl(null);
		RunCommand rc = new RunCommand();
		rc.setIpAddress("localhost");
		rc.setCommand("ipconfig /all");
		System.out.println(ws.runCommand(rc).getMessage());
		
		/*
		log.debug("testLogin - start method");
		LoginRequest req = new LoginRequest();
		req.setUsername("sorin");
		req.setPassword("slavic");
		log.debug("testLogin - create client");
		GenericSoapClient<LoginRequest, LoginResponse> client = new GenericSoapClient<LoginRequest, LoginResponse>("http://localhost:9080/mobilecmd/ws/", req);
		
		log.debug("testLogin - call web service");
		client.callWebService();
		
		log.debug("testLogin - get response");
		log.debug(client.getResponseObject().getUserID());
		*/
		log.info("testLogin - OK");
	} 
	
	public static void main(String[] args) throws Exception {
		(new TestClient()).testLogin();
	}
}
