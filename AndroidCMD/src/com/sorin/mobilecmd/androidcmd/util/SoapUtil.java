package com.sorin.mobilecmd.androidcmd.util;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public class SoapUtil {
	
	public static final String namespace = AndroidCMDProperties.getWebServicesNamespace();
	
	public static final void addProperty(SoapObject soapObject, String name, Object value) {
		PropertyInfo property = new PropertyInfo();
		property.setNamespace(namespace);
		property.setName(name);
		property.setValue(value);
		soapObject.addProperty(property);
	}
}
