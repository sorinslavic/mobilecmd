package com.sorin.mobilecmd.androidcmd;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ErrorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	        
	    requestWindowFeature(Window.FEATURE_LEFT_ICON);	        
	    setContentView(R.layout.error_activity);
	    
	    final TextView errorText = (TextView) findViewById(R.id.errorText);
	    String errorMessage = getIntent().getStringExtra("errorMessage");
	    errorText.setText(errorMessage);
	    
	    getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, 
	                android.R.drawable.ic_dialog_alert);
	}
}
