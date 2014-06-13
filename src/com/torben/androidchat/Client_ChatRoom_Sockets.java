package com.torben.androidchat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client_ChatRoom_Sockets implements Client_ChatRoom {
	
	Socket socket_;
	
	private BufferedReader input_;
	private BufferedWriter output_;
	
	private String host_;
	private int port_;
	
	public Client_ChatRoom_Sockets(String host, int port) throws IOException, UnknownHostException {
			host_ = host;
			port_ = port;

	}
	
	@Override
	public boolean addParticipant(String name) throws IOException {
		String msg = "name="+name;
		output_.write(msg);
		output_.newLine();
		output_.flush();
		return false;
	}

	@Override
	public boolean removeParticipant(String name) throws IOException {
		// TODO Auto-generated method stub
		String msg = "remove_name="+name;
		output_.write(msg);
		output_.newLine();
		output_.flush();
		return false;
	}

	@Override
	public boolean addTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		String msg = "add_topic="+topic;
		output_.write(msg);
		output_.newLine();
		output_.flush();
		return false;
	}

	@Override
	public boolean removeTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		String msg = "remove_topic="+topic;
		output_.write(msg);
		output_.newLine();
		output_.flush();
		return false;
	}

	@Override
	public boolean joinTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		String msg = "join_topic="+topic;
		output_.write(msg);
		output_.newLine();
		output_.flush();
		return false;
	}

	@Override
	public boolean leaveTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		String msg = "leave_topic="+topic;
		output_.write(msg);
		output_.newLine();
		output_.flush();
		return false;
	}

	@Override
	public boolean sendMessage(String message) throws IOException {
		// TODO Auto-generated method stub
		String msg = "message="+message;
		output_.write(msg);
		output_.newLine();
		output_.flush();
		return false;
	}

	@Override
	public String getMessages() throws IOException {
		// TODO Auto-generated method stub
		String msg = "action=";
		output_.write(msg);
		output_.newLine();
		output_.flush();
		
		String messages = null;
		while(messages == null)
		{
			messages = input_.readLine();
		}
		return messages;
	}

	@Override
	public void disconnect() throws IOException
	{
		output_.close();
		input_.close();
		new Thread()
		{
			@Override
		    public void run()
		    {
		        threadDissconnect();
		    }
		}.start();
	}

	
	private void threadDissconnect()
	{
		try {
			socket_.close();
			Client.Instance().finishConnectionState(true);
		} catch (IOException e) {
			Client.Instance().finishConnectionState(false);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void connect(){
		// TODO Auto-generated method stubs
		new Thread()
		{
			@Override
		    public void run()
		    {
				threadConnect();
		    }
		}.start();
	}
	
	private void threadConnect()
	{
		try {
			socket_ = new Socket(host_, port_);
			input_ = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
			output_ = new BufferedWriter(new OutputStreamWriter(socket_.getOutputStream()));
			Client.Instance().finishConnectionState(true);
		} catch (UnknownHostException e) {
			Client.Instance().finishConnectionState(false);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			Client.Instance().finishConnectionState(false);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
