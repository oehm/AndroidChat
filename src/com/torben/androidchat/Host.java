package com.torben.androidchat;

import java.io.IOException;

import lipermi.exception.LipeRMIException;
import lipermi.handler.CallHandler;
import lipermi.net.Server;
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
	private Host_RMI_Executer hostRMIExecuter_ = null;
	private CallHandler callHandler_;
	private Server server_;
	
	public String hostSockets = null;
	public int portSockets= 0;
	public String hostRPC = null;
	public int portRPC= 0;
	public String hostRMI = null;
	public int portRMI= 0;
	
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
			hostRPCThread_ = new Thread(new Host_ThreadMain_RPC());
			hostRPCThread_.start();
		}
		else {
			if(hostRPCThread_ == null) return;
			Log.v("Host:","RPC got turned off");
			hostRPCThread_.interrupt();
			hostRPCThread_ = null;
		}
	}
	
	public boolean getRMIState(){
		return hostRMIExecuter_ != null;
	}
	
	public void setRMIState(boolean on) {
		// TODO Auto-generated method stub
		if(on){
			if(hostRMIExecuter_ != null) return;
			Log.v("Host:","RMI got turned on");
			callHandler_ = new CallHandler();
			hostRMIExecuter_ = new Host_RMI_Executer_Impl();
			server_ = new Server();
			try {
				callHandler_.registerGlobal(Host_RMI_Executer.class, hostRMIExecuter_);
				server_.bind(4455, callHandler_);
				hostRMI = IPHelper.getIPAddress(true);
				portRMI = 4455;
			} catch (LipeRMIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			if(hostRMIExecuter_ == null) return;
			Log.v("Host:","RMI got turned off");
			server_.close();
			hostRMI= null;
			portRMI=0;
			hostRMIExecuter_ = null;
		}
	}
}
