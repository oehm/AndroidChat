package com.torben.androidchat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.conn.util.InetAddressUtils;

import android.util.Log;

public class Host_ThreadMain_Sockets  implements Runnable{
	
	private ServerSocket serverSocket_;
	
	private List<Thread> clientThreads_;

	@Override
	public void run()
	{
		clientThreads_ = new ArrayList<Thread>();
				
		try {
			serverSocket_ = new ServerSocket(0);
			Log.v("Host", "IP: "+getIPAddress(true));
			Log.v("Host", "Port: "+serverSocket_.getLocalPort());
			
			Host.Instance().ip= getIPAddress(true);
			Host.Instance().port= serverSocket_.getLocalPort();
			
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
				Thread thread = new Thread(new Host_ThreadConnection_Sockets(client));
				
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

		Host.Instance().ip= null;
		Host.Instance().port= 0;
		
		Log.v("Host:","Sockets: closed all connections");
	}
	
	public String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr); 
                        if (useIPv4) {
                            if (isIPv4) 
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim<0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }
}