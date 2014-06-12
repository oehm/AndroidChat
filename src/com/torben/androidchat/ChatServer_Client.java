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
	
	private boolean isConnected_;
	
	private String host_;
	private int port_;
	
	private ChatRoom chatRoom_;
	
	public void setConnectionType(ConnectionType type)
	{
		if(connectionType_ == type) return;
		try {
			disconnect();
			connectionType_ = type;
			connect(host_, port_);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void connect(String host, int port) throws IOException, UnknownHostException{
		// TODO Auto-generated method stub
		if(isConnected_) return;
		
		switch(connectionType_){
		case rest:
			chatRoom_ = new ChatRoom_Rest(host);
			break;
			
		case sockets:
			chatRoom_ = new ChatRoom_Sockets(host,port);
			break;
		}

	}

	public void disconnect() throws IOException {
		// TODO Auto-generated method stub

		if(!isConnected_) return;
		
		chatRoom_.disconnect();		
		chatRoom_ = null;
	}

	public ChatRoom getChatRoom() {
		// TODO Auto-generated method stub
		return chatRoom_;
	}

}
