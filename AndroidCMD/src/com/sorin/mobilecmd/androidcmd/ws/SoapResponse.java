package com.sorin.mobilecmd.androidcmd.ws;

import org.ksoap2.serialization.SoapObject;

public interface SoapResponse {
	void convertFromSoapObject(SoapObject soapObject);
}
