package com.sorin.mobilecmd.androidcmd.ws;

import android.util.Log;

import com.sorin.mobilecmd.androidcmd.ConnectionActivity;

public class ClientRunnable implements Runnable {
	private static final String TAG = ClientRunnable.class.getSimpleName();
	
	private final GenericSoapClient<? extends SoapRequest, ? extends SoapResponse> client;
	private final ConnectionActivity activity;
	
	public ClientRunnable(GenericSoapClient<? extends SoapRequest, ? extends SoapResponse> client, ConnectionActivity activity) {
		this.client = client;
		this.activity = activity;
	}

	@Override
	public void run() {
		Log.d(TAG, "run - Start the connection thread!");
		try {
			Log.d(TAG, "run - before web service call");
			client.callWebService();
			
			if (client.hasFault())
				activity.error(client.getFaultMessage(), null);
			Log.d(TAG, "run - after web service call");
			activity.ready(client.getResponseList());
		} catch (Exception e) {
			Log.d(TAG, "run - we have error " + e);
			e.printStackTrace();
			activity.error("", e);
		}
		Log.d(TAG, "run - End");
	}

}
