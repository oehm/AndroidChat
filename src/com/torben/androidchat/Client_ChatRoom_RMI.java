package com.torben.androidchat;

import java.io.IOException;
import java.net.UnknownHostException;

import lipermi.handler.CallHandler;
import lipermi.net.Client;

public class Client_ChatRoom_RMI implements Client_ChatRoom {


	private String host_;
	private int port_;
	
	private Host_RMI_Executer executer_;
	private CallHandler callHandler_;
	private Client client_;
	private String topic_ = null;
	private String name_ = null;
	
	public Client_ChatRoom_RMI(String host, int port) throws IOException, UnknownHostException {
		host_ = host;
		port_ = port;

}
	
	@Override
	public boolean addParticipant(String name) throws IOException {
		// TODO Auto-generated method stub
			name_  = name;
		return true;
	}

	@Override
	public boolean removeParticipant(String name) throws IOException {
		// TODO Auto-generated method stub
		name_ = null;
		return true;
	}

	@Override
	public boolean addTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		executer_.addTopic(topic);
		return true;
	}

	@Override
	public boolean removeTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		executer_.removeTopic(topic);
		return true;
	}

	@Override
	public boolean joinTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		if(name_!=null){
			topic_  = topic;
			executer_.addParticipant(name_, topic_);
			return true;
		}
		return false;
	}

	@Override
	public boolean leaveTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		if(name_!=null){
			topic_ = null;
			executer_.removeParticipant(name_, topic);
			return true;
		}
		return false;
	}

	@Override
	public boolean sendMessage(String message) throws IOException {
		// TODO Auto-generated method stub
		if(name_!=null&&topic_!=null){
			executer_.sendMessage(name_, topic_, message);
		}
		return true;
	}

	@Override
	public String getMessages() throws IOException {
		// TODO Auto-generated method stub
		if(topic_!= null){
			return executer_.getMessages(topic_);
		}
		return null;
	}

	@Override
	public void disconnect() throws IOException {
		// TODO Auto-generated method stub
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
				try {
					client_.close();
					ClientApp.Instance().finishConnectionState(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					ClientApp.Instance().finishConnectionState(false);
					e.printStackTrace();
				}
		    }
		});

		thread.start(); 
	}

	@Override
	public void connect() throws IOException {
		// TODO Auto-generated method stub
		callHandler_ = new CallHandler();
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		        try {
		        	client_ = new Client(host_, port_, callHandler_);
		    		executer_ = (Host_RMI_Executer)client_.getGlobal(Host_RMI_Executer.class);
		    		ClientApp.Instance().finishConnectionState(true);
		        } catch (Exception e) {
		        	ClientApp.Instance().finishConnectionState(false);
		        	e.printStackTrace();
		        }
		    }
		});

		thread.start(); 
		
	}

}
