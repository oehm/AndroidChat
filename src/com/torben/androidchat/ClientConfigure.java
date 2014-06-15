package com.torben.androidchat;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.torben.androidchat.ClientApp.ConnectionType;

public class ClientConfigure extends Activity {

	private EditText editName_;
	private EditText editTopic_;
	private EditText editHost_;
	private EditText editPort_;
	private ToggleButton toggleConnection_;
	private RadioGroup radioGroupType_;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_configure);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		setupEdits();
		setupConnectionToggle();
		setupRadioGroupSetter();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_configure, menu);
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
	
	private void setupEdits() {
		editHost_ = (EditText)findViewById(R.id.text_client_host);
		editPort_ = (EditText)findViewById(R.id.text_client_port);
		editName_ = (EditText)findViewById(R.id.text_client_username);
		editTopic_ = (EditText)findViewById(R.id.text_client_topic);
		
		
		if(ClientApp.Instance().host_!=null) editHost_.setText(ClientApp.Instance().host_);
		if(ClientApp.Instance().port_!=0) editPort_.setText(""+ClientApp.Instance().port_);
		if(ClientApp.Instance().userName_!=null) editName_.setText(ClientApp.Instance().userName_);
		if(ClientApp.Instance().topic_!=null) editTopic_.setText(ClientApp.Instance().topic_);
		
		
		editHost_.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString()!=null) ClientApp.Instance().host_= s.toString();
			}
		});
		editPort_.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				int port = 0;
				try{
					port = Integer.parseInt(s.toString());
				}
				catch(NumberFormatException e){
					
				}
				
				if(port != 0) ClientApp.Instance().port_= port;
			}
		});
		editName_.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString()!=null) ClientApp.Instance().userName_= s.toString();
			}
		});
		editTopic_.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString()!=null) ClientApp.Instance().topic_= s.toString();
			}
		});
	}
	
	private void setupConnectionToggle(){
		toggleConnection_ = (ToggleButton) findViewById(R.id.toggle_client_connection);
		toggleConnection_.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    @Override
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        // Save the state here
		    	if(isChecked){
					Log.v("Client:","connecting");
								
					String host = editHost_.getText().toString();
					int port=0;
					try {
						port = Integer.parseInt(editPort_.getText().toString());
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
					}
					
					ClientApp.Instance().host_ = host;
					ClientApp.Instance().port_ = port;
					ClientApp.Instance().connect();
				}
				else {
					Log.v("Client:","disconnecting");
					
					ClientApp.Instance().disconnect();
				}
		    	refreshStatus(toggleConnection_);
		    }
		});
		
		refreshStatus(toggleConnection_);
		
		
	}
	
	private void setupRadioGroupSetter(){
		radioGroupType_ = (RadioGroup) findViewById(R.id.radioGroup_client_type);
		radioGroupType_.clearCheck();
		RadioButton rb;
		switch (ClientApp.Instance().connectionType_) {
		case sockets:
			rb = (RadioButton)findViewById(R.id.radioButton_client_type_sockets);
			rb.setChecked(true);
			break;
		case rpc:
			rb = (RadioButton)findViewById(R.id.radioButton_client_type_rpc);
			rb.setChecked(true);
			break;
		case rmi:
			rb = (RadioButton)findViewById(R.id.radioButton_client_type_rmi);
			rb.setChecked(true);
			break;

		default:
			break;
		}
		
		radioGroupType_.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
				case R.id.radioButton_client_type_sockets:
					ClientApp.Instance().connectionType_=ConnectionType.sockets;
					break;

				case R.id.radioButton_client_type_rpc:
					ClientApp.Instance().connectionType_=ConnectionType.rpc;
					break;
					
				case R.id.radioButton_client_type_rmi:
					ClientApp.Instance().connectionType_=ConnectionType.rmi;
					break;
					
				default:
					break;
				}

            }

        });
	}
	
	public void refreshStatus(View view){
		switch (ClientApp.Instance().getConnectionStatus()) {
		case connected:
			toggleConnection_.setChecked(true);
			toggleConnection_.setTextOn(this.getString(R.string.toggle_client_turn_on));
			break;
			
		case connecting:
			toggleConnection_.setChecked(true);
			toggleConnection_.setTextOn(this.getString(R.string.toggle_client_turning_on));
			break;
			
		case disconnected:
			toggleConnection_.setChecked(false);
			toggleConnection_.setTextOff(this.getString(R.string.toggle_client_turn_off));
			break;
			
		case disconnecting:
			toggleConnection_.setChecked(false);
			toggleConnection_.setTextOff(this.getString(R.string.toggle_client_turning_off));
			break;

		default:
			break;
		}
    }
}
