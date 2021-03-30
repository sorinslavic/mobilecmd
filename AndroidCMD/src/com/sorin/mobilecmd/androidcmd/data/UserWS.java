package com.sorin.mobilecmd.androidcmd.data;

import org.ksoap2.serialization.SoapObject;

import com.sorin.mobilecmd.androidcmd.util.SoapUtil;
import com.sorin.mobilecmd.androidcmd.ws.SoapRequest;
import com.sorin.mobilecmd.androidcmd.ws.SoapResponse;

public class UserWS implements SoapResponse, SoapRequest {

	private int userID;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	
	public UserWS() {		
	}
	
	@Override
	public void convertToSoapObject(SoapObject soapObject) {
		SoapUtil.addProperty(soapObject, "Username", getUserName());
		SoapUtil.addProperty(soapObject, "Password", getPassword());		
	}

	/**
	 * <element maxOccurs="1" minOccurs="1" name="UserID" type="int"/>
	 * <element maxOccurs="1" minOccurs="1" name="Username" type="string"/>
	 * <element maxOccurs="1" minOccurs="1" name="Password" type="string"/>
	 * <element maxOccurs="1" minOccurs="1" name="FirstName" type="string"/>
	 * <element maxOccurs="1" minOccurs="1" name="LastName" type="string"/>
	 */
	@Override
	public void convertFromSoapObject(SoapObject soapObject) {
		this.setUserID(Integer.parseInt(soapObject.getPropertyAsString("UserID")));
		this.setUserName(soapObject.getPropertyAsString("Username"));
		this.setPassword(soapObject.getPropertyAsString("Password"));
		this.setFirstName(soapObject.getPropertyAsString("FirstName"));
		this.setLastName(soapObject.getPropertyAsString("LastName"));
		
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getDisplayName() {
		String ret = getFirstName() + " " + getLastName();
		
		if (ret.length() == 1)
			return getUserName();
		return ret;
	}

}
