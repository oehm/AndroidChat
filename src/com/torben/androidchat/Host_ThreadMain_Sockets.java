/*
 * Made by Tobias Hoffmann and Tobias Pretzl
 */
package com.torben.androidchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Host_ThreadMain_Sockets  implements Runnable{
	
	private ServerSocket serverSocket_;
	
	private List<Thread> clientThreads_; //list fo all connected clients

	@Override
	public void run()
	{
		clientThreads_ = new ArrayList<Thread>();
				
		try {
			serverSocket_ = new ServerSocket(0);
			Log.v("Host", "IP: "+IPHelper.getIPAddress(true));
			Log.v("Host", "Port: "+serverSocket_.getLocalPort());
			
			HostApp.Instance().hostSockets= IPHelper.getIPAddress(true);
			HostApp.Instance().portSockets= serverSocket_.getLocalPort();
			
			serverSocket_.setSoTimeout(2000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		while (!Thread.currentThread().isInterrupted())
		{
			for(Thread t : clientThreads_){
				if(t.isInterrupted()){
					clientThreads_.remove(t); // remove all "dead" clientss
				}
			}
			Socket client = null;
			try {
				client = serverSocket_.accept(); //waits for client to connect
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			if(client != null)
			{
				Thread thread = new Thread(new Host_ThreadConnection_Sockets(client)); // create new thread for new client
				thread.start();
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

		HostApp.Instance().hostSockets= null;
		HostApp.Instance().portSockets= 0;
		
		Log.v("Host:","Sockets: closed all connections");
	}
}