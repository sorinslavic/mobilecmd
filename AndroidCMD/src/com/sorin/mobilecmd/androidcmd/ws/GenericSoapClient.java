package com.sorin.mobilecmd.androidcmd.ws;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.sorin.mobilecmd.androidcmd.util.AndroidCMDProperties;

public class GenericSoapClient<Req extends SoapRequest, Res extends SoapResponse> {
	private static final String TAG = GenericSoapClient.class.getSimpleName();
	
	/**
	 * The <code>requestObject</code> of type <code>Req</code>, 
	 * a class defined in the generic instantiation of this object, representing the Request Object Class Type;
	 */
	private Req requestObject;
	
	/**
	 * The <code>responseObject</code> of type <code>Res</code>, 
	 * a class defined in the generic instantiation of this object, representing the Response Object Class Type;
	 */
	private final List<Res> responseList;
	
	private final Res responseInstance;
	
	private boolean hasFault;
	private String faultMessage;
		
	private static final String namespace = AndroidCMDProperties.getWebServicesNamespace();	
	private static final String wsdl = AndroidCMDProperties.getWebServicesWsdl();
	private static final URL url = AndroidCMDProperties.getWebServicesUrl();
	
	private final String methodName;
	private final SoapObject soapRequest;
	
	/**
	 * Constructor using a String <code>url</code> representing the URL address of the WebService we wish to interogate	
	 * @param urlString
	 * @throws UnsupportedOperationException
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws ParserConfigurationException
	 * @throws MalformedURLException
	 */
	public GenericSoapClient(Req reqObject, String methodName, Res responseObject) {		
		this.methodName = methodName;
		this.requestObject = reqObject;
		this.responseList = new ArrayList<Res>();
		responseList.add(responseObject);
		
		responseInstance = responseObject;
		
		soapRequest = new SoapObject(namespace, methodName); //set up request
		requestObject.convertToSoapObject(soapRequest);	
	}
    
	
	/**
	 * Do a AndroidHttpTransport call to the Web Service specified at the <code>url</code> address
	 * First we will create a SoapSerializationEnvelope request, using the <code>soapRequest</code> member of this Object
	 * We will call the Web Service and convert and retain the received SOAPMessage in the <code>soapResponse</code> member of this Object 
	 * @throws IOException 
	 * @throws XmlPullParserException 
	 */
	@SuppressWarnings("unchecked")
	public void callWebService() throws IOException, XmlPullParserException {
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
		envelope.setOutputSoapObject(soapRequest);
		HttpTransportSE httpTransport = new HttpTransportSE(wsdl);
		httpTransport.debug = true;
		Log.i(TAG, "before call");
		try {
			httpTransport.call(url + methodName, envelope);
		} catch (IOException e) {
			Log.i(TAG, "IO Exception on call " + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (XmlPullParserException e) {
			Log.i(TAG, "XmlPullParserException on call " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
		Log.i(TAG, "the sent message was: " + httpTransport.requestDump);
		
		hasFault = false;
		try {		
			Log.i(TAG, "before getResponse");

			Log.i(TAG, "the response message was: " + httpTransport.responseDump);
			Log.i(TAG, "response value is: " + envelope.getResponse());
			SoapObject response = (SoapObject) envelope.bodyIn;
			Log.i(TAG, "response object is: " + response);
			
			if (response.getPropertyCount() > 0) {
				Object prop0 = response.getProperty(0);
				if (prop0 instanceof SoapObject) {
					responseList.get(0).convertFromSoapObject((SoapObject) prop0);
					
					for (int i = 1; i < response.getPropertyCount(); i++) {
						SoapObject child = (SoapObject) response.getProperty(i);
						Res addToList = (Res) responseInstance.getClass().newInstance();						
						addToList.convertFromSoapObject(child);
						
						responseList.add(addToList);
					}
				} else if (prop0 instanceof SoapPrimitive) {
					responseList.get(0).convertFromSoapObject(response);
				} else {
					Log.i(TAG, "no match for class!: " + prop0.getClass().getCanonicalName());
					SoapFault sf = new SoapFault();
					sf.faultstring = "Could not parse the response SOAP message!";
					throw sf;
				}
			}
		} catch (SoapFault sf) {
			Log.i(TAG, "WE HAVE FAULT!!!!");
			Log.i(TAG, sf.toString());
			faultMessage = sf.faultstring;
			hasFault = true;
		} catch (Exception e) {
			Log.i(TAG, "WE HAVE Exception!!!!");
			Log.i(TAG, e.getMessage());
			faultMessage = e.getMessage();
			hasFault = true;
		}
		
		Log.i(TAG, "the response message was: " + httpTransport.responseDump);
    }
    
    /**
     * 
     * @return The <code>responseList</code> of response objects that are instances of the response class attributed in this generic class
     */
	public List<Res> getResponseList() {
		return responseList;
	}
	
	/**
     * 
     * @return The <code>responseObject</code> that is an instance of the response class attributed in this generic class
     * Note! - if the response was actually a list you will only get the FIRST element.
     */
	public Res getResponseObject() {
		 return responseList.get(0);
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
	 * @return <code>false</code> if the SOAP request completed successfully; <code>true</code> otherwise
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
}
