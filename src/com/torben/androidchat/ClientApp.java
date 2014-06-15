package com.torben.androidchat;

import java.io.IOException;
import java.net.UnknownHostException;

import android.util.Log;

public class ClientApp {
	private static ClientApp instance_ = null;
	
	public static ClientApp Instance(){
		if(instance_ == null){
			instance_ = new ClientApp();
		}
		return instance_;
	}
	
	public enum ConnectionType {sockets, rpc, rmi};
	public ConnectionType connectionType_ = ConnectionType.sockets;
	
	public enum ConnectionStatus {connected, connecting, disconnected, disconnecting};
	private ConnectionStatus connectionStatus_ = ConnectionStatus.disconnected;
	
	private Client_ChatRoom chatRoom_;

	public String host_ = "";
	public int port_ = 0;
	public String userName_ = "";
	public String topic_ = "";
	
	
	private ClientApp(){
		chatRoom_ = null;;
	}
	
	public ConnectionStatus getConnectionStatus(){
		return connectionStatus_;
	}
	
	public void connect(){
		if(connectionStatus_!= ConnectionStatus.disconnected) return;
		
		switch(connectionType_){
		case rpc:
			try {
				chatRoom_ = new Client_ChatRoom_RPC(host_,port_);
				connectionStatus_ = ConnectionStatus.connecting;
				chatRoom_.connect();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
			
		case rmi:
			try {
				chatRoom_ = new Client_ChatRoom_RMI(host_,port_);
				connectionStatus_ = ConnectionStatus.connecting;
				chatRoom_.connect();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
			
		case sockets:
			try {
				chatRoom_ = new Client_ChatRoom_Sockets(host_,port_);
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
	
	public Client_ChatRoom getChatroom(){
		return chatRoom_;
	}
}
