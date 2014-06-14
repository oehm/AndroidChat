package com.torben.androidchat;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.torben.androidchat.Client.ConnectionStatus;
import com.torben.androidchat.Client.ConnectionType;

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
		
		
		if(Client.Instance().host_!=null) editHost_.setText(Client.Instance().host_);
		if(Client.Instance().port_!=0) editPort_.setText(""+Client.Instance().port_);
		if(Client.Instance().userName_!=null) editName_.setText(Client.Instance().userName_);
		if(Client.Instance().topic_!=null) editTopic_.setText(Client.Instance().topic_);
		
		
		editHost_.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString()!=null) Client.Instance().host_= s.toString();
			}
		});
		editPort_.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString()!=null) Client.Instance().port_= Integer.parseInt(s.toString());
			}
		});
		editName_.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString()!=null) Client.Instance().userName_= s.toString();
			}
		});
		editTopic_.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString()!=null) Client.Instance().topic_= s.toString();
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
					int port = Integer.parseInt(editPort_.getText().toString());
					Client.Instance().host_ = host;
					Client.Instance().port_ = port;
					Client.Instance().connect();
				}
				else {
					Log.v("Client:","disconnecting");
					
					Client.Instance().disconnect();
				}
		    	toggleConnection_.setChecked(	Client.Instance().getConnectionStatus()==ConnectionStatus.connected ||
		    									Client.Instance().getConnectionStatus()==ConnectionStatus.connecting);
		    }
		});
		
		toggleConnection_.setChecked(	Client.Instance().getConnectionStatus()==ConnectionStatus.connected ||
										Client.Instance().getConnectionStatus()==ConnectionStatus.connecting);
		
		
	}
	
	private void setupRadioGroupSetter(){
		radioGroupType_ = (RadioGroup) findViewById(R.id.radioGroup_client_type);
		radioGroupType_.clearCheck();
		RadioButton rb;
		switch (Client.Instance().connectionType_) {
		case sockets:
			rb = (RadioButton)findViewById(R.id.radioButton_client_type_sockets);
			rb.setChecked(true);
			break;
		case rpc:
			rb = (RadioButton)findViewById(R.id.radioButton_client_type_rpc);
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
					Client.Instance().connectionType_=ConnectionType.sockets;
					break;

				case R.id.radioButton_client_type_rpc:
					Client.Instance().connectionType_=ConnectionType.rpc;
					break;
					
				default:
					break;
				}

            }

        });
	}
}
