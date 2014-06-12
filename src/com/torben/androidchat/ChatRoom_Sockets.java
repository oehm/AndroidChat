package com.torben.androidchat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatRoom_Sockets implements ChatRoom {
	
	Socket socket_;
	
	private BufferedReader input_;
	private BufferedWriter output_;
	
	public ChatRoom_Sockets(String host, int port) throws IOException, UnknownHostException {
			socket_ = new Socket(host, port);
			try {

				input_ = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
				output_ = new BufferedWriter(new OutputStreamWriter(socket_.getOutputStream()));

			} catch (IOException e) {
				e.printStackTrace();
			}
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
		socket_.close();
	}
}