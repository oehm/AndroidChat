package com.torben.androidchat;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

public class ClientConfigure extends Activity {

	private ToggleButton toggleConnection_;
	private EditText editHost_;
	private EditText editPort_;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_configure);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		editHost_ = (EditText)findViewById(R.id.text_client_host);
		editPort_ = (EditText)findViewById(R.id.text_client_port);
		
		//connection
		toggleConnection_ = (ToggleButton) findViewById(R.id.toggle_client_connection);
		toggleConnection_.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    @Override
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        // Save the state here
		    	handleConnectionChanged(isChecked);
		    }
		});
		toggleConnection_.setChecked(Client.Instance().getConnectionState());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_configure, menu);
		return true;
	}
	
	
	private void handleConnectionChanged(boolean on){
		if(on){
			if(Client.Instance().getConnectionState()) return;
			Log.v("Client:","connecting");
			
			String host = editHost_.getText().toString();
			int port = Integer.parseInt(editPort_.getText().toString());
			Client.Instance().connect(host, port);
			
			toggleConnection_.setChecked(Client.Instance().getConnectionState());
		}
		else {
			if(!Client.Instance().getConnectionState()) return;
			Log.v("Client:","disconnecting");
			
			Client.Instance().disconnect();
			
			toggleConnection_.setChecked(Client.Instance().getConnectionState());
		}
	}
}
