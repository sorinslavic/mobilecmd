package com.sorin.mobilecmd.androidcmd.data;

import org.ksoap2.serialization.SoapObject;

import com.sorin.mobilecmd.androidcmd.util.NameValue;
import com.sorin.mobilecmd.androidcmd.util.SoapUtil;
import com.sorin.mobilecmd.androidcmd.ws.SoapRequest;
import com.sorin.mobilecmd.androidcmd.ws.SoapResponse;

public class ClientFileWS implements NameValue, SoapResponse, SoapRequest {	
	private int clientFileId;
	private String path;
	private boolean folder;
	private boolean recursive;
	
	private int clientId;
	
	public ClientFileWS() {
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

	@Override
	public void convertToSoapObject(SoapObject soapObject) {
		SoapUtil.addProperty(soapObject, "ClientID", getClientId());
	}

	/**
	 * <element maxOccurs="1" minOccurs="1" name="ClientFileID" type="int"/>
	 * <element maxOccurs="1" minOccurs="1" name="FilePath" type="string"/>
	 * <element maxOccurs="1" minOccurs="1" name="Folder" type="boolean"/>
	 * <element maxOccurs="1" minOccurs="1" name="Recursive" type="boolean"/>
	 */
	@Override
	public void convertFromSoapObject(SoapObject soapObject) {
		this.setClientFileId(Integer.parseInt(soapObject.getPropertyAsString("ClientFileID")));
		this.setPath(soapObject.getPropertyAsString("FilePath"));
		this.setFolder(soapObject.getPropertyAsString("Folder").equalsIgnoreCase("true"));
		this.setRecursive(soapObject.getPropertyAsString("Recursive").equalsIgnoreCase("true"));
	}

	@Override
	public String getName() {
		return getPath();
	}

	@Override
	public Object getValue() {
		return "fileValue";
	}
}
