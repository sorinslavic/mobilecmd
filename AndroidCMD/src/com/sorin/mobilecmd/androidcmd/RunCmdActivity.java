package com.sorin.mobilecmd.androidcmd;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.sorin.mobilecmd.androidcmd.data.RunCommandWS;
import com.sorin.mobilecmd.androidcmd.data.RunResponseWS;
import com.sorin.mobilecmd.androidcmd.util.Const;
import com.sorin.mobilecmd.androidcmd.ws.GenericSoapClient;
import com.sorin.mobilecmd.androidcmd.ws.SoapResponse;

public class RunCmdActivity extends ConnectionActivity {
	private static final String TAG = RunCmdActivity.class.getSimpleName();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		Log.d(TAG, "we are logged in with user: " + AppObject.getAppObject().getUser().getDisplayName());		
		setContentView(R.layout.cmd_message);
		
		TextView title = (TextView)findViewById(R.id.title);
		String titleString = getIntent().getStringExtra(Const.RUN_COMMAND_COMMAND);
		if (titleString.length() > 15) 
			titleString = titleString.substring(0, 15) + "...";
				
		title.setText(titleString);
		
		callWS();
	}
	
	public void callWS() {
		RunCommandWS request = new RunCommandWS();
		RunResponseWS response = new RunResponseWS();
		request.setCommand(getIntent().getStringExtra(Const.RUN_COMMAND_COMMAND));
		request.setIpAddress(getIntent().getStringExtra(Const.RUN_COMMAND_IP_ADDRESS));
		GenericSoapClient<RunCommandWS, RunResponseWS> client = new GenericSoapClient<RunCommandWS, RunResponseWS>(request, "RunCommand", response);
		
		connect(client);
	}

	@Override
	public void readyToUse(List<? extends SoapResponse> response) {
		final RunResponseWS runResponse = (RunResponseWS) response.get(0);
		
		runOnUiThread(new Runnable() {
			public void run() {
				((EditText) findViewById(R.id.response)).setText(runResponse.getMessage());
			}
		});
	}

}
