package com.torben.androidchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Host_Thread_Sockets  implements Runnable{
	
	private int port_;
	private ServerSocket serverSocket_;
	
	private List<Thread> clientThreads_;

	@Override
	public void run()
	{
		clientThreads_ = new ArrayList<Thread>();
				
		try {
			serverSocket_ = new ServerSocket(port_);
			serverSocket_.setSoTimeout(2000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		while (!Thread.currentThread().isInterrupted())
		{
			Log.v("Host:","Sockets: waiting for connection");
			for(Thread t : clientThreads_){
				if(t.isInterrupted()){
					clientThreads_.remove(t);
				}
			}
			Socket client = null;
			try {
				client = serverSocket_.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			if(client != null)
			{
				Thread thread = new Thread(new Client_Thread_Socket(client));
				
				clientThreads_.add(thread);
			}
		}
		
		try {
			serverSocket_.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Thread t : clientThreads_){
			t.interrupt();
		}

		Log.v("Host:","Sockets: closed all connections");
	}
	
}