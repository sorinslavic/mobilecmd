package com.sorin.mobilecmd.androidcmd;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sorin.mobilecmd.androidcmd.data.ClientFileWS;
import com.sorin.mobilecmd.androidcmd.util.ComboAdapter;
import com.sorin.mobilecmd.androidcmd.util.Const;
import com.sorin.mobilecmd.androidcmd.ws.GenericSoapClient;
import com.sorin.mobilecmd.androidcmd.ws.SoapResponse;

public class ComputerFilesActivity extends ConnectionActivity {
	
	private static final String TAG = ComputerListActivity.class.getSimpleName();	
	private ListView fileListView;
	private ComboAdapter<ClientFileWS> optionsAdapter;
	private List<ClientFileWS> fileList;
	
	private String ipAddress;
		
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		Log.d(TAG, "we are logged in with user: " + AppObject.getAppObject().getUser().getDisplayName());		
		setContentView(R.layout.computer_files);
		
		TextView title = (TextView)findViewById(R.id.title);
		title.setText(getIntent().getStringExtra(Const.GET_FILES_INTENT_COMPUTER_NAME));
		
		ipAddress = getIntent().getStringExtra(Const.GET_FILES_INTENT_IP_ADDRESS);
	
		if (getIntent().getBooleanExtra(Const.GET_FILES_INTENT_ALLOW_CMD, true)) {
			findViewById(R.id.cmdView).setVisibility(View.VISIBLE);
			Button run = (Button) findViewById(R.id.run);
			run.setOnClickListener(new View.OnClickListener() {
				
	    		@Override
	    		public void onClick(View v) {
	    			Log.d(TAG, "click on button run");
	    			
	    			AlertDialog alertDialog = new AlertDialog.Builder(ComputerFilesActivity.this)
	    									.setTitle("Run")
	    									.setMessage("Are you sure you want to Run this Command?")
	    									.setPositiveButton("Yes", new DialogInterface.OnClickListener() {																	
	    																@Override
																		public void onClick(DialogInterface dialog, int which) {
																			runCmd();
																		}
																	})
											.setNegativeButton("No", new DialogInterface.OnClickListener() {																	
	    																@Override
																		public void onClick(DialogInterface dialog, int which) {
	    																	dialog.cancel();																		
																		}
																	})
	    									.create();
	    						
	    		    alertDialog.show();
	    		}
	    	});
		} else {
			findViewById(R.id.cmdView).setVisibility(View.GONE);
		}
		
		fileListView = (ListView) findViewById(R.id.fileList);
		fileList = new ArrayList<ClientFileWS>();
		
		fileListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final ClientFileWS current = fileList.get(arg2);
				
				AlertDialog alertDialog = new AlertDialog.Builder(ComputerFilesActivity.this)
										.setTitle("File")
										.setMessage("Are you sure you want to download the selected file? \n" + current.getPath())
										.setPositiveButton("Yes", new DialogInterface.OnClickListener() {																	
																	@Override
																	public void onClick(DialogInterface dialog, int which) {
																		// TODO Auto-generated method stub
																	
																	}
																})
										.setNegativeButton("No", new DialogInterface.OnClickListener() {																	
																	@Override
																	public void onClick(DialogInterface dialog, int which) {
																		// TODO Auto-generated method stub
																	
																	}
																})
										.create();
	
				alertDialog.show();
				
			}
		});
		
		callWS();		
	}
	
	public void runCmd() {
		String cmd = ((TextView) findViewById(R.id.cmdText)).getText().toString();
		
		if (cmd.trim().equals("")) {
			AlertDialog alertDialog = new AlertDialog.Builder(ComputerFilesActivity.this).create();  
		    alertDialog.setTitle("Required parameter");  
		    alertDialog.setMessage("Please input a Command!");
		    alertDialog.setCanceledOnTouchOutside(true);
		    alertDialog.show();
			return;
		}
		
		Intent i = new Intent(ComputerFilesActivity.this, RunCmdActivity.class);
		i.putExtra(Const.RUN_COMMAND_COMMAND, cmd);
		i.putExtra(Const.RUN_COMMAND_IP_ADDRESS, ipAddress);
		startActivity(i);
	}
	
	public void callWS() {
		ClientFileWS response = new ClientFileWS();
		ClientFileWS clientId = new ClientFileWS();
		clientId.setClientId(getIntent().getIntExtra(Const.GET_FILES_INTENT_CLIENT_ID, 0));
		GenericSoapClient<ClientFileWS, ClientFileWS> client = new GenericSoapClient<ClientFileWS, ClientFileWS>(clientId, "GetClientFilesRequest", response);
		
		connect(client);
	}

	@Override
	public void readyToUse(List<? extends SoapResponse> response) {
		for (SoapResponse responseObject : response)
			fileList.add((ClientFileWS) responseObject);
		
		runOnUiThread(new Runnable() {
			public void run() {
				optionsAdapter = new ComboAdapter<ClientFileWS>(ComputerFilesActivity.this.getApplication(),
						R.layout.field_option_details, fileList);
				fileListView.setAdapter(optionsAdapter);
			}
		});
	}

}
