package com.torben.androidchat;

import javax.servlet.ServletException;

import android.util.Log;

public class Host {
	private Host()
	{

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
	
	public String ip = null;
	public int port= 0;
	
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
	
	public boolean getRPCState(){
		return hostRPCThread_ != null;
	}
	
	public void setRPCState(boolean on) {
		// TODO Auto-generated method stub
		if(on){
			if(hostRPCThread_ != null) return;
			Log.v("Host:","RPC got turned on");
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
		else {
			if(hostRPCThread_ == null) return;
			Log.v("Host:","RPC got turned off");
			hostRPCThread_.interrupt();
			hostRPCThread_ = null;
		}
	}
}
