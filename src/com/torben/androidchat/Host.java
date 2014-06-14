package com.torben.androidchat;

import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import android.util.Log;

public class Host {
	private Host()
	{
		Host_ThreadMain_JsonRpcServlet th_rpc = null;
		try {
			th_rpc  = new Host_ThreadMain_JsonRpcServlet();
			th_rpc.init();	
			hostRPCThread_ = new Thread(th_rpc);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.v("RPC",th_rpc.getServletInfo());
		hostRPCThread_.start();
	}
	
	private static Host instance_ = null;

	public static Host Instance(){
		if(instance_ == null){
			instance_ = new Host();
		}
		return instance_;
	}
	
	private Thread hostSocketThread_ = null;
	private Thread hostRPCThread_ = null;
	
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
