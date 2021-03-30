package com.sorin.mobilecmd.androidcmd;

import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sorin.mobilecmd.androidcmd.data.UserWS;
import com.sorin.mobilecmd.androidcmd.util.AndroidCMDProperties;
import com.sorin.mobilecmd.androidcmd.ws.GenericSoapClient;
import com.sorin.mobilecmd.androidcmd.ws.SoapResponse;

public class AndroidCMDActivity extends ConnectionActivity {
	private static final String TAG = AndroidCMDActivity.class.getSimpleName();
	
	EditText usernameBox;
	EditText passwordBox;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		@SuppressWarnings("unused")
		AndroidCMDProperties props = new AndroidCMDProperties(this);
		Log.d(TAG, "Creating the main activity - login");
		
    	setContentView(R.layout.login);        
    	usernameBox = (EditText) findViewById(R.id.username);
    	passwordBox = (EditText) findViewById(R.id.password);
    	
    	Button login = (Button) findViewById(R.id.login);
    	login.setOnClickListener(new View.OnClickListener() {
			
    		@Override
    		public void onClick(View v) {
    			Log.d(TAG, "click on button login");
    			if (validate())
    				post();
    		}
    	});
        
    	Button register = (Button) findViewById(R.id.register);
    	register.setOnClickListener(new View.OnClickListener() {
			
    		@Override
    		public void onClick(View v) {
    			Log.d(TAG, "click on button register");
    			Uri uri = AndroidCMDProperties.getRegistrationUri();
    			Log.d(TAG, "the path is: " + uri.toString());
    			Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
    			startActivity(browserIntent);
    		}
    	});
	}
	
	private final boolean validate() {
		String username = usernameBox.getText().toString();
		String password = passwordBox.getText().toString();
				
		if (username.trim().equals("")) {
			AlertDialog alertDialog = new AlertDialog.Builder(AndroidCMDActivity.this).create();  
		    alertDialog.setTitle("Required parameter");  
		    alertDialog.setMessage("Please input your username");
		    alertDialog.setCanceledOnTouchOutside(true);
		    alertDialog.show();
			return false;
		}
		if (password.trim().equals("")) {
			AlertDialog alertDialog = new AlertDialog.Builder(AndroidCMDActivity.this).create();  
		    alertDialog.setTitle("Required parameter");  
		    alertDialog.setMessage("Please input your password");
		    alertDialog.setCanceledOnTouchOutside(true);
		    alertDialog.show();
		    return false;
		}
		return true;
	}
	
	private final void post() {
		findViewById(R.id.LinearLayout2).setVisibility(View.GONE);
		
		String username = usernameBox.getText().toString();
		String password = passwordBox.getText().toString();
		
		UserWS response = new UserWS();
		UserWS user = new UserWS();		
		user.setUserName(username);
		user.setPassword(password);
		
		Log.d(TAG, "create client for request: " + user);
		GenericSoapClient<UserWS, UserWS> client = new GenericSoapClient<UserWS, UserWS>(user, "LoginRequest", response);
		
		connect(client);
		/*UserWS userWS = new UserWS();
		userWS.setUserID(1);
		userWS.setFirstName("remove me");
		AppObject.getAppObject().setUser(userWS);
		
		Intent i = new Intent(AndroidCMDActivity.this, ComputerListActivity.class);
    	startActivity(i);*/
	}

	@Override
	public void readyToUse(List<? extends SoapResponse> response) {
		UserWS userWS = (UserWS) response.get(0);
		AppObject.getAppObject().setUser(userWS);
		
		Intent i = new Intent(AndroidCMDActivity.this, ComputerListActivity.class);
    	startActivity(i);
		finish();
	}
}