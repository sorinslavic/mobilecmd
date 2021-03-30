package com.sorin.mobilecmd.androidcmd;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.sorin.mobilecmd.androidcmd.ws.ClientRunnable;
import com.sorin.mobilecmd.androidcmd.ws.GenericSoapClient;
import com.sorin.mobilecmd.androidcmd.ws.SoapCallback;
import com.sorin.mobilecmd.androidcmd.ws.SoapRequest;
import com.sorin.mobilecmd.androidcmd.ws.SoapResponse;

public abstract class ConnectionActivity extends Activity implements SoapCallback {
	private static final String TAG = ConnectionActivity.class.getSimpleName();
	
	private static final Executor executor = AppObject.getAppObject().getExecutor();

	private View loadingView;
	private boolean inProgress;
	
	protected final synchronized void connect(GenericSoapClient<? extends SoapRequest, ? extends SoapResponse> client) {
		if (inProgress) {
			error(getString(R.string.running));
			return;
		}
		inProgress = true;
		showLoading();
		
		Runnable runnable = new ClientRunnable(client, this);
		executor.execute(runnable);
	}
	
	/** 
	 * Simple method that shows the loading view
	 */
	private void showLoading() {
		if (loadingView == null) {
			loadingView = findViewById(R.id.loading);
			if (loadingView == null) {
				return;
			}
		}
        if (loadingView.getVisibility() != View.VISIBLE) {        	
            final Animation fadeIn = AnimationUtils.loadAnimation(ConnectionActivity.this, R.anim.fade_in);
            loadingView.setVisibility(View.VISIBLE);
            loadingView.startAnimation(fadeIn);
        }
	}
    
	/**
	 * Simple method that hides the loading view.
	 */
    private void hideLoading() {
		runOnUiThread(
				new Runnable() {
					public void run() {					
						if (loadingView == null) {
							loadingView = findViewById(R.id.loading);
							if (loadingView == null) {
								return;
							}
						}
				        if (loadingView.getVisibility() != View.GONE) {
				            final Animation fadeOut = AnimationUtils.loadAnimation(ConnectionActivity.this, R.anim.fade_out);
				            loadingView.setVisibility(View.GONE);       
				            loadingView.startAnimation(fadeOut);
				        }
					}
				}
		);
    }    

	public abstract void readyToUse(List<? extends SoapResponse> response);

	/**
	 * The ready call for this method - forward the page to the ReadyToUse implementation; 
	 */
	@Override
	public final void ready(List<? extends SoapResponse> response) {
		try {
			readyToUse(response);
		} finally {
			hideLoading();
			synchronized(this) {
				inProgress = false;
			}			
		}
	}

	/**
	 * The error call for this method - either user generated or Error caused;
	 */
	@Override
	public final void error(String message, Exception e) {
		try {
			Log.e(TAG, "error - Message: " + message);
			if (e != null) {
				Log.e(TAG, "error - Exception: " + e.getMessage());
			}
			hideLoading();
			final String userMessage;
			if (e == null){
				userMessage = message;
			} else if (e instanceof IOException) {
				userMessage = "Connection or server error: " + e.getMessage();
			} else if (e instanceof XmlPullParserException) {
				userMessage = "XML parse error: " + e.getMessage(); 
			} else if (e instanceof ClassCastException) {
				userMessage =  "XML field type error: " + e.getMessage();
			} else {
				userMessage = "Unknown error: " + e.getMessage(); 
			}
			error(userMessage);
		} finally {
			synchronized(this) {
				inProgress = false;
			}
		}
	}
	
	/**
	 * This method is called by the {@link #error(String, Exception)} after it processes
	 * the error information. If the subclasses make changes to the 
	 * GUI from this method they must use {@link #runOnUiThread(Runnable)} method.
	 * @param parser
	 * @param userMessage
	 */
	protected void error(final String userMessage) {
		runOnUiThread(
				new Runnable() {
					public void run() {
				        Intent intent = new Intent(ConnectionActivity.this, ErrorActivity.class);
				        intent.putExtra("errorMessage", getString(R.string.error) + " (" + userMessage + ")");            	    
				        startActivity(intent);
					}
		});
	}
}
