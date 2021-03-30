package com.sorin.mobilecmd.androidcmd.data;

import org.ksoap2.serialization.SoapObject;

import com.sorin.mobilecmd.androidcmd.ws.SoapResponse;

public class RunResponseWS implements SoapResponse {

    private boolean success;
    private String message;
    
    public RunResponseWS() {		
	}
    
    /**
     * <element name="Success"		type="boolean" minOccurs="1" maxOccurs="1"></element>
     * <element name="Message"		type="string" minOccurs="1" maxOccurs="1"></element>
     */
	@Override
	public void convertFromSoapObject(SoapObject soapObject) {
		this.setSuccess(soapObject.getPropertyAsString("Success").equalsIgnoreCase("true"));
		this.setMessage(soapObject.getPropertyAsString("Message"));
	}

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean value) {
        this.success = value;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String value) {
        this.message = value;
    }
}
