package com.torben.androidchat;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class HostConfigure extends Activity {

	private ToggleButton toggleSockets_;
	private ToggleButton toggleRPC_;
	private ToggleButton toggleRMI_;
	private TextView textHostSockets_;
	private TextView textPortSockets_;
	private TextView textHostRPC_;
	private TextView textPortRPC_;
	private TextView textHostRMI_;
	private TextView textPortRMI_;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_configure);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		setupSockets();
		setupRPC();
		setupRMI();
		
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
	
	private void setupSockets(){
		toggleSockets_ = (ToggleButton) findViewById(R.id.toggle_host_socket);
		toggleSockets_.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    @Override
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        // Save the state here
		    	Host.Instance().setSocketsState(isChecked);
		    }
		});
		toggleSockets_.setChecked(Host.Instance().getSocketState());
		
		textHostSockets_ = (TextView) findViewById(R.id.text_host_ip_sockets);
		textPortSockets_ = (TextView) findViewById(R.id.text_host_port_sockets);
		
		if(Host.Instance().hostSockets!=null)
			textHostSockets_.setText(Host.Instance().hostSockets);
		
		if(Host.Instance().portSockets!=0)
			textPortSockets_.setText(Host.Instance().portSockets+"");
	}
	
	private void setupRPC(){
		toggleRPC_ = (ToggleButton) findViewById(R.id.toggle_host_rpc);
		toggleRPC_.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    @Override
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        // Save the state here
		    	Host.Instance().setRPCState(isChecked);
		    }
		});
		toggleRPC_.setChecked(Host.Instance().getRPCState());
		
		textHostRPC_ = (TextView) findViewById(R.id.text_host_ip_rpc);
		textPortRPC_ = (TextView) findViewById(R.id.text_host_port_rpc);
		
		if(Host.Instance().hostRPC!=null)
			textHostRPC_.setText(Host.Instance().hostRPC);
		
		if(Host.Instance().portRPC!=0)
			textPortRPC_.setText(Host.Instance().portRPC+"");
	}
	
	private void setupRMI(){
		toggleRMI_ = (ToggleButton) findViewById(R.id.toggle_host_rmi);
		toggleRMI_.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    @Override
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        // Save the state here
		    	Host.Instance().setRMIState(isChecked);
		    }
		});
		toggleRMI_.setChecked(Host.Instance().getRMIState());
		
		textHostRMI_ = (TextView) findViewById(R.id.text_host_ip_rmi);
		textPortRMI_ = (TextView) findViewById(R.id.text_host_port_rmi);
		
		if(Host.Instance().hostRMI!=null)
			textHostRMI_.setText(Host.Instance().hostRMI);
		
		if(Host.Instance().portRMI!=0)
			textPortRMI_.setText(Host.Instance().portRMI+"");
	}
	
	/* Called when the user clicks the refresh sockets button.*/
    public void refreshSockets(View view){
    	if(Host.Instance().hostSockets!=null)
			textHostSockets_.setText(Host.Instance().hostSockets);
    	else
    		textHostSockets_.setText(R.string.text_host_ip);
		
		if(Host.Instance().portSockets!=0)
			textPortSockets_.setText(Host.Instance().portSockets+"");
    	else
    		textPortSockets_.setText(R.string.text_host_port);
		
		toggleSockets_.setChecked(Host.Instance().getSocketState());
	}
    
    public void refreshRPC(View view){
    	if(Host.Instance().hostRPC!=null)
			textHostRPC_.setText(Host.Instance().hostRPC);
    	else
    		textHostRPC_.setText(R.string.text_host_ip);
		
		if(Host.Instance().portRPC!=0)
			textPortRPC_.setText(Host.Instance().portRPC+"");
    	else
    		textPortRPC_.setText(R.string.text_host_port);
		
		toggleRPC_.setChecked(Host.Instance().getRPCState());
	}
    
    public void refreshRMI(View view){
    	if(Host.Instance().hostRMI!=null)
			textHostRMI_.setText(Host.Instance().hostRMI);
    	else
    		textHostRMI_.setText(R.string.text_host_ip);
		
		if(Host.Instance().portRMI!=0)
			textPortRMI_.setText(Host.Instance().portRMI+"");
    	else
    		textPortRMI_.setText(R.string.text_host_port);
		
		toggleRMI_.setChecked(Host.Instance().getRMIState());
	}
}
