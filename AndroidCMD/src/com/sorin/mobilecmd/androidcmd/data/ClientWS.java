package com.sorin.mobilecmd.androidcmd.data;

import org.ksoap2.serialization.SoapObject;

import com.sorin.mobilecmd.androidcmd.util.NameValue;
import com.sorin.mobilecmd.androidcmd.util.SoapUtil;
import com.sorin.mobilecmd.androidcmd.ws.SoapRequest;
import com.sorin.mobilecmd.androidcmd.ws.SoapResponse;

public class ClientWS implements NameValue, SoapResponse, SoapRequest {	
	private int clientId;
	private String ipAddress;
	private String computerName;
	private boolean allowCommands;
	private int userId;
	
	public ClientWS() {		
	}
	

	@Override
	public void convertToSoapObject(SoapObject soapObject) {
		SoapUtil.addProperty(soapObject, "UserID", getUserId());
	}

	/**
	 * <element maxOccurs="1" minOccurs="1" name="ClientID" type="int"/>
	 * <element maxOccurs="1" minOccurs="1" name="ComputerName" type="string"/>
	 * <element maxOccurs="1" minOccurs="1" name="IpAddress" type="string"/>
	 * <element maxOccurs="1" minOccurs="1" name="AllowCommands" type="boolean"/>
	 */
	@Override
	public void convertFromSoapObject(SoapObject soapObject) {
		this.setClientId(Integer.parseInt(soapObject.getPropertyAsString("ClientID")));
		this.setComputerName(soapObject.getPropertyAsString("ComputerName"));
		this.setIpAddress(soapObject.getPropertyAsString("IpAddress"));
		this.setAllowCommands(soapObject.getPropertyAsString("AllowCommands").equalsIgnoreCase("true"));
	}
	
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getComputerName() {
		return computerName;
	}
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	public boolean isAllowCommands() {
		return allowCommands;
	}
	public void setAllowCommands(boolean allowCommands) {
		this.allowCommands = allowCommands;
	}


	@Override
	public String getName() {
		return getComputerName() + " [" + getIpAddress() + "]";
	}


	@Override
	public Object getValue() {
		return "value";
	}

}
