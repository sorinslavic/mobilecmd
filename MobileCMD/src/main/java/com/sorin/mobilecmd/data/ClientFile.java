package com.sorin.mobilecmd.data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * CREATE TABLE ClientFiles (
	    ClientFileID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	    ClientID INT NOT NULL,
	    Path VARCHAR(250),
	    IsFolder INT NOT NULL DEFAULT 0,
	    IsRecursive INT NOT NULL DEFAULT 0,
	    DateAdded DATETIME NOT NULL,
	    LastAccessDate DATETIME,
	    FOREIGN KEY (ClientID) REFERENCES Clients(ClientID) ON DELETE CASCADE    
	);
 */
public class ClientFile implements Serializable {
	private static final long serialVersionUID = 6919322300786500380L;
	
	private int clientFileId;
	private String path;
	private boolean folder;
	private boolean recursive;
	private Date dateAdded;
	private Date lastAccessDate;
	
	private int clientId;
	
	public ClientFile() {
		
	}
	
	public int getClientFileId() {
		return clientFileId;
	}
	public void setClientFileId(int clientFileId) {
		this.clientFileId = clientFileId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isFolder() {
		return folder;
	}
	public void setFolder(boolean folder) {
		this.folder = folder;
	}
	public boolean isRecursive() {
		return recursive;
	}
	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public Date getLastAccessDate() {
		return lastAccessDate;
	}
	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}
	
	public String getLastAccess() {
		if (getLastAccessDate() == null)
			return "";
		SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		return sdf.format(getLastAccessDate());
	}
	
	public String getAdded() {
		if (getDateAdded() == null)
			return "";
		SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		return sdf.format(getDateAdded());
	}
	
	public static List<String> getPathForFiles(List<ClientFile> files) {
		List<String> pathList = new ArrayList<String>();
		
		for (ClientFile file : files)
			pathList.add(file.getPath());
		
		return pathList;
	}
}
