package com.sorin.mobilecmd.androidcmd.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.sorin.mobilecmd.androidcmd.R;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

public class AndroidCMDProperties {
	private static final String TAG = AndroidCMDProperties.class.getSimpleName();
	private static final Properties props = new Properties();
	
	public AndroidCMDProperties(Context c) {
		Log.d(TAG, "Generating properties");
		if (props.isEmpty()) {
			Log.d(TAG, "This is the first time we will load the sources");
			InputStream rawResource = c.getResources().openRawResource(R.raw.androidcmd);
			try {
				props.load(rawResource);
				Log.d(TAG, "SUCCESS In loading!");
			} catch (IOException e) {
				Log.d(TAG, "FAILURE - Could not LOAD properties!");
			}
		}
	}
	
	private static final Properties getProps() {
		return props;
	}
		
	public static Uri getRegistrationUri() {
		Log.d(TAG, "someone want the registrationURI: " + getProps().getProperty("registrationURI"));
		return Uri.parse(getProps().getProperty("registrationURI"));
	}
	
	public static URL getWebServicesUrl() {
		Log.d(TAG, "someone wants the web service url: " + getProps().getProperty("webServicesPath"));
		try {
			return new URL(getProps().getProperty("webServicesPath"));
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public static String getWebServicesNamespace() {
		Log.d(TAG, "someone wants the getWebServicesNamespace: " + getProps().getProperty("webServicesNamespace"));
		return getProps().getProperty("webServicesNamespace");
	}

	public static String getWebServicesWsdl() {
		Log.d(TAG, "someone wants the getWebServicesWsdl: " + getProps().getProperty("webServicesWsdl"));
		return getProps().getProperty("webServicesWsdl");
	}
}
