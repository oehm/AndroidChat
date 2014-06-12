package com.torben.androidchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client_Thread_Socket implements Runnable{

	private Socket socket_;
	
	private BufferedReader input_;
	
	public Client_Thread_Socket(Socket socket){
		socket_ = socket;
		try {

			input_ = new BufferedReader(new InputStreamReader(socket_.getInputStream()));

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
		
		if(keyWord == "name")
		{
			String name = input.substring(eqIdx+1); 
			try {
				ChatRoom_Host.Instance().addParticipant(name,"");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(keyWord == "remove_name")
		{
			String name = input.substring(eqIdx+1);;
			try {
				ChatRoom_Host.Instance().removeParticipant(name,"");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(keyWord == "message")
		{
			String message = input.substring(eqIdx+1);
			//ChatRoom_Host.Instance().sendMessage(topic, message);
		}
		else if(keyWord == "add_topic")
		{
			
		}
		else if(keyWord == "remove_toppic")
		{
			
		}
		else if(keyWord == "action")
		{
			
		}
		else
			return;
		
		
		
	}
}
