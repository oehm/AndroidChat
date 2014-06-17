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

public class Host_ThreadMain_RPC  implements Runnable {

	private ServerSocket serverSocket_;
	private List<Thread> clientThreads_; //lit for all connected clients

	@Override
	public void run()
	{
		clientThreads_ = new ArrayList<Thread>();
				
		try {
			serverSocket_ = new ServerSocket(0);
			Log.v("Host", "IP: "+IPHelper.getIPAddress(true));
			Log.v("Host", "Port: "+serverSocket_.getLocalPort());
			
			HostApp.Instance().hostRPC= IPHelper.getIPAddress(true);
			HostApp.Instance().portRPC= serverSocket_.getLocalPort();
			
			serverSocket_.setSoTimeout(2000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		while (!Thread.currentThread().isInterrupted())
		{
			for(Thread t : clientThreads_){
				if(t.isInterrupted()){
					clientThreads_.remove(t); // remove all "dead" threads
				}
			}
			Socket client = null;
			try {
				client = serverSocket_.accept(); //wait for client to connect; max 2sec
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			if(client != null)
			{
				Thread thread = null;
				try {
					thread = new Thread(new Host_ThreadConnection_RPC(client));
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
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

		HostApp.Instance().hostRPC= null;
		HostApp.Instance().portRPC= 0;
		
		Log.v("Host:","Sockets: closed all connections");
	}
	
}