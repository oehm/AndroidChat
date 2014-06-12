package com.torben.androidchat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client_Thread_Socket implements Runnable{

	private Socket socket_;	
	private BufferedReader input_;
	private BufferedWriter output_;
	
	private String topic_;
	private String name_;
	
	public Client_Thread_Socket(Socket socket){
		socket_ = socket;
		topic_ = null;
		name_ = null;
		try {

			input_ = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
			output_ = new BufferedWriter(new OutputStreamWriter(socket_.getOutputStream()));

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
	}
	
	private void parseInput(String input){
		//parse and call correct functions on the ChatRoom_Host instance
		int eqIdx;
		eqIdx = input.indexOf('=');
		
		if(eqIdx==-1)
		{
			//invalid message
			return;
		}
		String keyWord = input.substring(0, eqIdx);
		
		if(keyWord.equals("name"))
		{
			String name = input.substring(eqIdx+1); 
			try {
				if(ChatRoom_Host.Instance().addParticipant(name,""))
					name_ = name; 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(keyWord.equals("remove_name"))
		{
			if(name_ == null) return;
			
			String name = input.substring(eqIdx+1);;
			try {
				ChatRoom_Host.Instance().removeParticipant(name,"");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(keyWord.equals("message"))
		{
			if(name_ == null || topic_ ==null) return;
			
			String message = input.substring(eqIdx+1);
			try {
				ChatRoom_Host.Instance().sendMessage(name_,topic_,message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(keyWord.equals("add_topic"))
		{
			String topic = input.substring(eqIdx+1);
			try {
				ChatRoom_Host.Instance().addTopic(topic);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(keyWord.equals("remove_toppic"))
		{
			String topic = input.substring(eqIdx+1);
			try {
				ChatRoom_Host.Instance().removeTopic(topic);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(keyWord.equals("join_topic"))
		{
			if(name_ == null) return;
			String topic =input.substring(eqIdx+1);
			topic_ = topic;
			try {
				ChatRoom_Host.Instance().addParticipant(name_, topic_);
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
				String messages = ChatRoom_Host.Instance().getMessages(topic_);
				if(messages!= null)
					output_.write(messages);
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
