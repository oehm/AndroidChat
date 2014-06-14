package com.torben.androidchat;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ClientChat extends Activity {

	EditText input_;
	LinearLayout display_;
	ScrollView scrollview_;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_chat);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		input_= (EditText)findViewById(R.id.edit_client_chatinput);
		display_= (LinearLayout)findViewById(R.id.layout_client_chat);
		scrollview_= (ScrollView)findViewById(R.id.scroll_client_chat);
		
		Client_ChatRoom chatroom = Client.Instance().getChatroom();
		if(chatroom != null){
			try {
				chatroom.addParticipant(Client.Instance().userName_);
				chatroom.addTopic(Client.Instance().topic_);
				chatroom.joinTopic(Client.Instance().topic_);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		refreshMessages(null);
	}

	@Override
	protected void onPause(){
        super.onPause();
        
		Client_ChatRoom chatroom = Client.Instance().getChatroom();
		if(chatroom != null){
			try {
				chatroom.leaveTopic(Client.Instance().topic_);
				chatroom.removeParticipant(Client.Instance().userName_);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_chat, menu);
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
	
	public void sendMessage(View view){
		Client_ChatRoom chatroom = Client.Instance().getChatroom();
		if(chatroom != null){
			try {
				chatroom.sendMessage(input_.getText().toString());
				input_.setText("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		refreshMessages(view);
    }

	public void refreshMessages(View view){
		String message = null;
		Client_ChatRoom chatroom = Client.Instance().getChatroom();
		if(chatroom != null){
			try {
				message = chatroom.getMessages();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(message!=null){
			display_.removeAllViews();
			
			String[] messages = message.split(";;");
			
			TextView newLine;
			for (String m : messages) {
				newLine = new TextView(this);
				newLine.setText(m);
		    	display_.addView(newLine);
			}
			
	    	scrollview_.post(new Runnable() { 
	    	    public void run() { 
	    	    	scrollview_.fullScroll(ScrollView.FOCUS_DOWN); 
	    	    } 
	    	}); 
		}
    }
}
