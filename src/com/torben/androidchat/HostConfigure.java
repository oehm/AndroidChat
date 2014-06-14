package com.torben.androidchat;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class HostConfigure extends Activity {

	private ToggleButton toggleSockets_;
	private TextView textIp_;
	private TextView textPort_;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_configure);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//sockets:
		toggleSockets_ = (ToggleButton) findViewById(R.id.toggle_host_socket);
		toggleSockets_.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    @Override
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        // Save the state here
		    	Host.Instance().setSocketsState(isChecked);
		    }
		});
		toggleSockets_.setChecked(Host.Instance().getSocketState());
		
		textIp_ = (TextView) findViewById(R.id.text_host_ip);
		textPort_ = (TextView) findViewById(R.id.text_host_port);
		
		if(Host.Instance().ip!=null)
			textIp_.setText(Host.Instance().ip);
		
		if(Host.Instance().port!=0)
			textPort_.setText(Host.Instance().port+"");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.host_configure, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
