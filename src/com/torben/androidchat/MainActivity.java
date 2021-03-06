/*
 * Made by Tobias Hoffmann and Tobias Pretzl
 */
package com.torben.androidchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
//main UIto acces configuration screens and chat itself
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    public void startHostConfigure(View view){
    	Intent intent = new Intent(this, HostConfigure.class);
    	startActivity(intent);
    }
    
    public void startClientConfigure(View view){
    	Intent intent = new Intent(this, ClientConfigure.class);
    	startActivity(intent);
    }
    
    public void startClientChat(View view){
    	Intent intent = new Intent(this, ClientChat.class);
    	startActivity(intent);
    }

}
