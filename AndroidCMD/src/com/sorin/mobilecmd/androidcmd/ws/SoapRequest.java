package com.sorin.mobilecmd.androidcmd.ws;

import org.ksoap2.serialization.SoapObject;

public interface SoapRequest {
	void convertToSoapObject(SoapObject soapObject);
}
