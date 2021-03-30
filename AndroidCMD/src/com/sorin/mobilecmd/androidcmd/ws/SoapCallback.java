package com.sorin.mobilecmd.androidcmd.ws;

import java.util.List;

/**
 * This interface is used by the SoapParser class and its subclasses 
 * to inform its client about the result of XML parsing. 
 * @see SoapParser
 * @see Batch
 */
public interface SoapCallback {
	public void ready(List<? extends SoapResponse> response);	
	public void error(String message, Exception e);

}
