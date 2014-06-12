package com.torben.androidchat;

import java.io.IOException;
import java.net.UnknownHostException;


public class ChatServer_Client {

	private static ChatServer_Client instance_ = null;
	
	public static ChatServer_Client Instance(){
		if(instance_ == null){
			instance_ = new ChatServer_Client();
		}
		return instance_;
	}

	public enum ConnectionType {sockets, rest};
	private ConnectionType connectionType_ = ConnectionType.sockets;
	
	private boolean isConnected_ = false;
	
	private String host_;
	private int port_;
	
	private ChatRoom chatRoom_;
	
	public void setConnectionType(ConnectionType type)
	{
		if(connectionType_ == type) return;
		
		disconnect();
		connectionType_ = type;
		connect(host_, port_);
	}
	
	public void connect(String host, int port){
		// TODO Auto-generated method stub
		if(isConnected_) return;
		
		switch(connectionType_){
		case rest:
			try {
				chatRoom_ = new ChatRoom_Rest(host);
				chatRoom_.connect();
				isConnected_ = true;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
			
		case sockets:
			try {
				chatRoom_ = new ChatRoom_Sockets(host,port);
				chatRoom_.connect();
				isConnected_ = true;
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
		// TODO Auto-generated method stub

		if(!isConnected_) return;

		try {
			chatRoom_.disconnect();
			chatRoom_ = null;
			isConnected_ = false;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ChatRoom getChatRoom() {
		// TODO Auto-generated method stub
		return chatRoom_;
	}

	public boolean isConnected(){
		return isConnected_;
	}
}
