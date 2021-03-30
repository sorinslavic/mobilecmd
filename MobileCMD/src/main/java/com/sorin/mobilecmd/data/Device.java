package com.sorin.mobilecmd.data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * CREATE TABLE Devices (
    DeviceID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    DeviceIMEI BIGINT NOT NULL,
    UserID INT NOT NULL,
    DeviceType VARCHAR(100) NOT NULL,
    DeviceName VARCHAR(100),
    LastAccessDate DATETIME,
    PhoneNumber VARCHAR(100),
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE    
);
 *
 */
public class Device implements Serializable {
	private static final long serialVersionUID = -4466800151955938271L;
	private int deviceId;
	private long deviceImei;
	private String deviceType;
	private String deviceName;
	private Date lastAccessDate;
	private String phoneNumber;
	
	private int userId;
	
	public Device() {		
	}
	
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public long getDeviceImei() {
		return deviceImei;
	}
	public void setDeviceImei(long deviceImei) {
		this.deviceImei = deviceImei;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public Date getLastAccessDate() {
		return lastAccessDate;
	}
	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getLastAccess() {
		if (getLastAccessDate() == null)
			return "";
		SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		return sdf.format(getLastAccessDate());
	}
}
