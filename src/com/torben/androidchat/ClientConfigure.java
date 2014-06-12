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
		toggleConnection_.setChecked(ChatServer_Client.Instance().isConnected());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_configure, menu);
		return true;
	}
	
	private void handleConnectionChanged(boolean on){
		if(on){
			if(ChatServer_Client.Instance().isConnected()) return;
			Log.v("Client:","connecting");
			String host = editHost_.getText().toString();
			int port = Integer.parseInt(editPort_.getText().toString());
			
			ChatServer_Client.Instance().connect(host, port);
		}
		else {
			if(!ChatServer_Client.Instance().isConnected()) return;
			Log.v("Client:","disconnecting");
			ChatServer_Client.Instance().disconnect();
		}
	}
}
