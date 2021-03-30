package com.sorin.mobilecmd.androidcmd.data;

import org.ksoap2.serialization.SoapObject;

import com.sorin.mobilecmd.androidcmd.util.SoapUtil;
import com.sorin.mobilecmd.androidcmd.ws.SoapRequest;

public class RunCommandWS implements SoapRequest {

    private String command;
    private String ipAddress;
    
    public RunCommandWS() {    	
    }
    
    /**
     * <element name="Command" 	type="string" minOccurs="1" maxOccurs="1"></element>
     * <element name="IpAddress"	type="string" minOccurs="1" maxOccurs="1"></element>
     */
	@Override
	public void convertToSoapObject(SoapObject soapObject) {
		SoapUtil.addProperty(soapObject, "Command", getCommand());
		SoapUtil.addProperty(soapObject, "IpAddress", getIpAddress());
	}
	
    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
