package com.torben.androidchat;

import java.io.IOException;
import java.net.UnknownHostException;

import android.util.Log;

public class Client {
	private static Client instance_ = null;
	
	public static Client Instance(){
		if(instance_ == null){
			instance_ = new Client();
		}
		return instance_;
	}
	
	public enum ConnectionType {sockets, rest};
	private ConnectionType connectionType_ = ConnectionType.sockets;
	
	public enum ConnectionStatus {connected, connecting, disconnected, disconnecting};
	private ConnectionStatus connectionStatus_ = ConnectionStatus.disconnected;
	
	private Client_ChatRoom chatRoom_;
	
	private Client(){
		chatRoom_ = null;;
	}
	
	public boolean getConnectionState(){
		return connectionStatus_ != ConnectionStatus.disconnected;
	}
	
	public void connect(String host, int port){
		if(connectionStatus_!= ConnectionStatus.disconnected) return;
		
		switch(connectionType_){
		case rest:
			try {
				chatRoom_ = new Client_ChatRoom_RPC(host);
				connectionStatus_ = ConnectionStatus.connecting;
				chatRoom_.connect();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
			
		case sockets:
			try {
				chatRoom_ = new Client_ChatRoom_Sockets(host,port);
				connectionStatus_ = ConnectionStatus.connecting;
				chatRoom_.connect();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
	
	public void disconnect(){
		if(connectionStatus_!=ConnectionStatus.connected) return;

		try {
			chatRoom_.disconnect();
			connectionStatus_ = ConnectionStatus.disconnecting;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void finishConnectionState(boolean success){
		Log.v("Client", "operation success: "+success);
		
		if(success){
			if(connectionStatus_ == ConnectionStatus.connecting) connectionStatus_ = ConnectionStatus.connected;
			if(connectionStatus_ == ConnectionStatus.disconnecting) connectionStatus_ = ConnectionStatus.disconnected;
		}
		else{
			if(connectionStatus_ == ConnectionStatus.connecting) connectionStatus_ = ConnectionStatus.disconnected;
			if(connectionStatus_ == ConnectionStatus.disconnecting) connectionStatus_ = ConnectionStatus.connected;
		}
	}
}
