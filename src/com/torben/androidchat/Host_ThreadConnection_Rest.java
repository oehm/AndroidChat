package com.torben.androidchat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class Host_ThreadConnection_Rest implements Runnable{

	private HttpURLConnection connection_;	
	private BufferedReader input_;
	private BufferedWriter output_;
	
	private String topic_;
	private String name_;
	
	public Host_ThreadConnection_Rest(HttpURLConnection connection){
		connection_ = connection;
		topic_ = null;
		name_ = null;
		try {
			input_ = new BufferedReader(new InputStreamReader(connection_.getInputStream()));
			output_ = new BufferedWriter(new OutputStreamWriter(connection_.getOutputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.currentThread().isInterrupted()) {

			try {
				String read = input_.readLine();
				parseInput(read);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			connection_.disconnect();
			output_.close();
			input_.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parseInput(String input){
		//parse and call correct functions on the ChatRoom_Host instance
		JSONObject jsonIn;
		String keyWord;
		String value;
		try {
			jsonIn = new JSONObject(input);		
			keyWord = jsonIn.getString("Command");
			value = jsonIn.getString("Value");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}

		
		if(keyWord.equals("name"))
		{
			name_ = value; 
		}
		else if(keyWord.equals("remove_name"))
		{
			if(name_ == null || topic_ == null) return;
			try {
				Host_ChatRoom.Instance().removeParticipant(value,topic_);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(keyWord.equals("message"))
		{
			if(name_ == null || topic_ ==null) return;
			try {
				Host_ChatRoom.Instance().sendMessage(name_,topic_,value);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(keyWord.equals("add_topic"))
		{
			try {
				Host_ChatRoom.Instance().addTopic(value);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(keyWord.equals("remove_toppic"))
		{
			try {
				Host_ChatRoom.Instance().removeTopic(value);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(keyWord.equals("join_topic"))
		{
			if(name_ == null) return;
			topic_ = value;
			try {
				Host_ChatRoom.Instance().addParticipant(name_, topic_);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(keyWord.equals("leave_toppic"))
		{
			topic_ = null;
		}
		
		else if(keyWord.equals("action"))
		{			
			if(topic_ == null) return;
			
			try {
				String messages = Host_ChatRoom.Instance().getMessages(topic_);
				JSONObject jsonOut = new JSONObject();
				try {
					jsonOut.put("value", messages);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					output_.write(jsonOut.toString());
					output_.newLine();
					output_.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		else
			return;		
	}
}
