package com.sorin.mobilecmd.ws.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.sorin.mobilecmd.util.MobileCMDPropertiesFacade;

public class GenericSoapClient<Req, Res> {
	private static final Logger log = Logger.getLogger(GenericSoapClient.class);
	
	/**
	 * The <code>requestObject</code> of type <code>Req</code>, 
	 * a class defined in the generic instantiation of this object, representing the Request Object Class Type;
	 */
	private Req requestObject;
	
	/**
	 * The <code>responseObject</code> of type <code>Res</code>, 
	 * a class defined in the generic instantiation of this object, representing the Response Object Class Type;
	 */
	private Res responseObject;
	
	private boolean hasFault;
	private String faultMessage;
	
	private String requestSOAPMessage;
	private String responseSOAPMessage;

	private SOAPConnectionFactory connectionFactory;
	private MessageFactory messageFactory;
	private TransformerFactory transformerFactory;
	private DocumentBuilderFactory documentBuilderFactory;
	private DocumentBuilder documentBuilder;	
	private JAXBContext jaxbContext;
	private Marshaller marshaller;	
	private Unmarshaller unmarshaller;
	private URL url;
	
	/**
	 * Constructor using a String <code>url</code> representing the URL address of the WebService we wish to interogate	
	 * @param urlString
	 * @throws UnsupportedOperationException
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws ParserConfigurationException
	 * @throws MalformedURLException
	 */
	public GenericSoapClient(String urlString, Req reqObject) throws UnsupportedOperationException, SOAPException, JAXBException, ParserConfigurationException, MalformedURLException {
		connectionFactory = SOAPConnectionFactory.newInstance();
		messageFactory = MessageFactory.newInstance();
		transformerFactory = TransformerFactory.newInstance();
		jaxbContext = JAXBContext.newInstance(MobileCMDPropertiesFacade.SCHEMA_PACKAGE);
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, MobileCMDPropertiesFacade.DEFAULT_ENCODING);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		unmarshaller = jaxbContext.createUnmarshaller();
		url = new URL(urlString);
		requestObject = reqObject;
	}
	
	/**
	 * 
	 * @return Marshal the <code>requestObject</code> member of this Object into a SOAPMessage
	 * @throws SOAPException
	 * @throws JAXBException
	 */
	private SOAPMessage createRequest() throws SOAPException, JAXBException {
    	Document document = documentBuilder.newDocument();
    	marshaller.marshal(requestObject, document);	    
	    
    	SOAPMessage message = messageFactory.createMessage();
	    SOAPBody soapBody = message.getSOAPBody();
	    soapBody.addDocument(document);
	    
        return message;     
    }
    
	/** 
	 * 
	 * @param response
	 * @return Unmarshal the SOAPMessage <code>response</code> into an Object (no schema class specified)
	 * @throws JAXBException
	 * @throws SOAPException
	 */
	private Object getResponse(SOAPMessage response) throws JAXBException, SOAPException {
        Document document = response.getSOAPBody().extractContentAsDocument();
        log.debug("getResponse - we unmarshall - body: " + response.getSOAPBody());
        log.debug("getResponse - we unmarshall - body content: " + response.getSOAPBody().getTextContent());
        log.debug("getResponse - we unmarshall - document: " + document);
        return unmarshaller.unmarshal(document);
    }
	
	/**
	 * Do a SOAPConnection call to the Web Service specified at the <code>url</code> address
	 * First we will create a SOAPMessage request, using the <code>requestObject</code> member of this Object
	 * We will call the Web Service and convert and retain the received SOAPMessage in the <code>responseObject</code> member of this Object
	 * If there are faults or exceptions we will throw them
	 * @throws SOAPException
	 * @throws IOException
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	public void callWebService() throws SOAPException, IOException, JAXBException {
        SOAPMessage request = createRequest();
        requestSOAPMessage = getSOAPMessage(request);
        log.debug("callWebService - Request: " + requestSOAPMessage);
        log.debug("callWebService - To: " + url);
        
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        responseSOAPMessage = getSOAPMessage(response);
        log.debug("callWebService - Response: " + responseSOAPMessage);
        
        if (!response.getSOAPBody().hasFault()) {
       		responseObject = (Res) getResponse(response);
        	hasFault = false;

        	log.debug("callWebService - Request completed successfully");
        }
        else {
            SOAPFault fault = response.getSOAPBody().getFault();
            faultMessage = fault.getFaultString();
            hasFault = true;
            
            log.debug("callWebService - Request completed with errors !");
            log.debug("callWebService - SOAP Fault Code : " + fault.getFaultCode());
            log.debug("callWebService - SOAP Fault String : " + fault.getFaultString());
        }
    }	

	/**
	 * 
	 * @param message
	 * @return The full String content of this <code>message</code>
	 * @throws SOAPException
	 * @throws IOException
	 */
    private String getSOAPMessage(SOAPMessage message) throws SOAPException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();		
		message.writeTo(baos);
		try {
			return prettyFormat( baos.toString());
		} catch (TransformerException e) {
			return baos.toString();		
		}
	}
    
    /**
     * 
     * @return The <code>responseObject</code> that is an instance of the response class attributed in this generic class
     */
	public Res getResponseObject() {
		return responseObject;
	}
	
	/**
	 * 
	 * @return The <code>requestObject</code> that is an instance of the response class attributed in this generic class
	 */
	public Req getRequestObject() {
		return requestObject;
	}
	
	/**
	 * 
	 * @return <code>true</code> if the SOAP request completed successfully; <code>false</code> otherwise
	 */
	public boolean hasFault() {
		return hasFault;
	}
	
	/**
	 * 
	 * @return The <code>faultMessage</code> of the request
	 */
	public String getFaultMessage() {
		return faultMessage;
	}
	
	/**
	 * Returns an user-friendly error message.
	 * @return
	 */
	public String getShortFaultMessage() {
		int idx = faultMessage.indexOf("At line");
		if(idx != -1) {
			String tmp = faultMessage.substring(0, idx);
			int idx0 = tmp.indexOf(":");
			return tmp.substring(idx0+1);
		}
		else 
			return faultMessage;
			
	}
	
	/**
	 * 
	 * @return SOAP request message
	 */
	public String getRequestSOAPMessage() {
		return requestSOAPMessage;
	}
	
	/**
	 * 
	 * @return SOAP response message
	 */
	public String getResponseSOAPMessage() {
		return responseSOAPMessage;
	}
	
	/**
	 * Formats a XML document.
	 * @param string
	 * @return
	 * @throws TransformerException
	 */
	public String prettyFormat(String string) throws TransformerException {
        Source xmlInput = new StreamSource(new StringReader(string));
        StreamResult xmlOutput = new StreamResult(new StringWriter());
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(xmlInput, xmlOutput);
        String message = xmlOutput.getWriter().toString();
        return message;
	}
	
}
