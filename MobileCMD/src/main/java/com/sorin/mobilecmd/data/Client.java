package com.sorin.mobilecmd.data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CREATE TABLE Clients (
    ClientID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    IpAddress VARCHAR(100) NOT NULL,
    ComputerName VARCHAR(100),
    DateInstalled DATETIME NOT NULL,
    LastAccessDate DATETIME,
    AllowCommands INT NOT NULL DEFAULT 0,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE    
);
 *
 */

public class Client implements Serializable {
	private static final long serialVersionUID = -3904164923934213136L;
	private int clientId;
	private String ipAddress;
	private String computerName;
	private Date dateInstalled;
	private Date lastAccessDate;
	private boolean allowCommands;
	
	private int userId;
	
	public Client() {		
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
	public Date getDateInstalled() {
		return dateInstalled;
	}
	public void setDateInstalled(Date dateInstalled) {
		this.dateInstalled = dateInstalled;
	}
	public Date getLastAccessDate() {
		return lastAccessDate;
	}
	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}
	public boolean isAllowCommands() {
		return allowCommands;
	}
	public void setAllowCommands(boolean allowCommands) {
		this.allowCommands = allowCommands;
	}
	
	public String getInstalled() {
		if (getDateInstalled() == null)
			return "";
		SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		return sdf.format(getDateInstalled());
	}
	
	public String getLastAccess() {
		if (getLastAccessDate() == null)
			return "";		
		SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		return sdf.format(getLastAccessDate());
	}
	
}
