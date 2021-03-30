package com.sorin.mobilecmd.androidcmd;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.sorin.mobilecmd.androidcmd.data.ClientWS;
import com.sorin.mobilecmd.androidcmd.util.ComboAdapter;
import com.sorin.mobilecmd.androidcmd.util.Const;
import com.sorin.mobilecmd.androidcmd.ws.GenericSoapClient;
import com.sorin.mobilecmd.androidcmd.ws.SoapResponse;

public class ComputerListActivity extends ConnectionActivity {
	private static final String TAG = ComputerListActivity.class.getSimpleName();
	
	private ListView computerListView;
	private ComboAdapter<ClientWS> optionsAdapter;
	private List<ClientWS> computerList;
		
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		Log.d(TAG, "we are logged in with user: " + AppObject.getAppObject().getUser().getDisplayName());		
		setContentView(R.layout.computer_list);
		
		TextView title = (TextView)findViewById(R.id.title);
		title.setText(AppObject.getAppObject().getUser().getDisplayName());
		
		computerListView = (ListView) findViewById(R.id.computerList);
		computerList = new ArrayList<ClientWS>();
		
		computerListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final ClientWS current = computerList.get(arg2);
				Intent getFilesActivity = new Intent(ComputerListActivity.this, ComputerFilesActivity.class);
				
				getFilesActivity.putExtra(Const.GET_FILES_INTENT_CLIENT_ID, current.getClientId());
				getFilesActivity.putExtra(Const.GET_FILES_INTENT_COMPUTER_NAME, current.getComputerName());
				getFilesActivity.putExtra(Const.GET_FILES_INTENT_IP_ADDRESS, current.getIpAddress());
				getFilesActivity.putExtra(Const.GET_FILES_INTENT_ALLOW_CMD, current.isAllowCommands());
				
		    	startActivity(getFilesActivity);
			}
		});
		
		callWS();		
	}
	
	public void callWS() {
		ClientWS response = new ClientWS();
		ClientWS clientWithUser = new ClientWS();
		clientWithUser.setUserId(AppObject.getAppObject().getUser().getUserID());
		GenericSoapClient<ClientWS, ClientWS> client = new GenericSoapClient<ClientWS, ClientWS>(clientWithUser, "GetClientsRequest", response);
		
		connect(client);
	}

	@Override
	public void readyToUse(List<? extends SoapResponse> response) {
		for (SoapResponse responseObject : response)
			computerList.add((ClientWS) responseObject);
		

		runOnUiThread(new Runnable() {
			public void run() {
				optionsAdapter = new ComboAdapter<ClientWS>(ComputerListActivity.this.getApplication(),
						R.layout.field_option_details, computerList);
				computerListView.setAdapter(optionsAdapter);
			}
		});
	}

}
