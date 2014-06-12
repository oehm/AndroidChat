package com.torben.androidchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Host_Thread_Sockets  implements Runnable{
	
	private int port_;
	private ServerSocket serverSocket_;
	
	private List<Socket> clients_;

	@Override
	public void run()
	{		
		try {
			serverSocket_ = new ServerSocket(port_);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		while (!Thread.currentThread().isInterrupted())
		{
			Socket client = null;
			try {
				client = serverSocket_.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(client != null)
			{
				clients_.add(client);
			}
		}
	}
	
}
	
	/*
class ServerThread implements Runnable {

	public void run() {
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(SERVERPORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (!Thread.currentThread().isInterrupted()) {

			try {

				socket = serverSocket.accept();

				CommunicationThread commThread = new CommunicationThread(socket);
				new Thread(commThread).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
*/