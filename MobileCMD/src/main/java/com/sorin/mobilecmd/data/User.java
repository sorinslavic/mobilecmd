package com.sorin.mobilecmd.data;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.sorin.mobilecmd.util.MobileCMDPropertiesFacade;
import com.sorin.mobilecmd.util.ParameterParser;
import com.sorin.mobilecmd.util.PasswordRestrictions;
import com.sorin.mobilecmd.util.SpringUtils;
import com.sorin.mobilecmd.util.exception.RequiredParameterException;

/** This class is the Java equivalent for the folowing table:
 * 	CREATE TABLE Users (
	    UserID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	    UserName VARCHAR(100) NOT NULL,
	    Password VARCHAR(100) NOT NULL,
	    DateJoined DATETIME NOT NULL,
	    FirstName VARCHAR(100) NOT NULL,
	    LastName VARCHAR(100) NOT NULL,
	);
*/

public class User extends Validated implements Serializable {
	private static final long serialVersionUID = 3833732132542377295L;
	private int userID;
	private String userName;
	private String password;
	private Date dateJoined;
	private String firstName;
	private String lastName;
	
	public User() {
		super();
	}
	
	public User(HttpServletRequest req) {
		super();
				
		String password2 = null;		
		firstName = ParameterParser.getStringParameter(req, "firstName");
		lastName = ParameterParser.getStringParameter(req, "lastName");
		
		PasswordRestrictions pr = SpringUtils.getBean(MobileCMDPropertiesFacade.class, req).getPasswordRestrictions();
		
		try {
			userName = ParameterParser.getRequiredStringParameter(req, "username", "User name");
			password = ParameterParser.getRequiredStringParameter(req, "password", "Password");
			password2 = ParameterParser.getRequiredStringParameter(req, "password2", "Validate Password");
		} catch (RequiredParameterException e) {
			setException(e);
			return;
		}
		
		if (!pr.validatePassword(password)) {
			addError("The entered password does not match crietria. \n" + pr.getHtmlFormat());
			return;
		}
		
		if (!this.password.equals(password2)) {
			addError("The entered password does not match Validation Password");			
		}
		
		validateParamLength(userName, 20, "Username");
		validateParamLength(password, 100, "Pasword");
		validateParamLength(firstName, 50, "First Name");
		validateParamLength(lastName, 50, "Last Name");
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
	public Date getDateJoined() {
		return dateJoined;
	}
	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
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
