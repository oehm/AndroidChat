package com.torben.androidchat;

import android.util.Log;

public class Host {
	private static Host instance_ = null;
	
	public static Host Instance(){
		if(instance_ == null){
			instance_ = new Host();
		}
		return instance_;
	}
	
	private Thread hostSocketThread_ = null;
	
	public boolean getSocketState(){
		return hostSocketThread_ != null;
	}
	
	public void setSocketsState(boolean on) {
		// TODO Auto-generated method stub
		if(on){
			if(hostSocketThread_ != null) return;
			Log.v("Host:","Sockets got turned on");
			hostSocketThread_ = new Thread(new Host_ThreadMain_Sockets());
			hostSocketThread_.start();
		}
		else {
			if(hostSocketThread_ == null) return;
			Log.v("Host:","Sockets got turned off");
			hostSocketThread_.interrupt();
			hostSocketThread_ = null;
		}
	}
}
