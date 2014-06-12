package com.torben.androidchat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import 	android.util.Log;

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
	
	/* Called when the user clicks the start Host button.*/
    public void startHost(View view){
    	Log.v("Button:","host");
    	Intent intent = new Intent(this, HostConfigure.class);
    	startActivity(intent);
    }
    
    /* Called when the user clicks the start Client button.*/
    public void startClient(View view){
    	Log.v("Button:","client");
    }

}
