package com.torben.androidchat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import com.torben.androidchat.JSONRPC.client.HttpJsonRpcClientTransport;
import com.torben.androidchat.JSONRPC.client.JsonRpcInvoker;

public class Client_ChatRoom_RPC implements Client_ChatRoom {

    private String host_;
    private int port_;
    
    private Socket socket_;
    
	private BufferedReader input_;
	private BufferedWriter output_;
    
    private HttpJsonRpcClientTransport transport_;
    private Client_ChatRoom RPCExec_;
	
	public Client_ChatRoom_RPC (String host,int port) throws IOException
	{
		host_ = host;
		port_ = port;
	}
	
	@Override
	public boolean addParticipant(String name) throws IOException {
		return RPCExec_.addParticipant(name);
	}
	
	

	@Override
	public boolean removeParticipant(String name) throws IOException {
		return RPCExec_.removeParticipant(name);
	}

	@Override
	public boolean addTopic(String topic) throws IOException {
		return RPCExec_.addTopic(topic);
	}

	@Override
	public boolean removeTopic(String topic) throws IOException {
		return RPCExec_.removeTopic(topic);
	}

	@Override
	public boolean joinTopic(String topic) throws IOException {
		return RPCExec_.joinTopic(topic);
	}

	@Override
	public boolean leaveTopic(String topic) throws IOException {
		return RPCExec_.leaveTopic(topic);
	}

	@Override
	public boolean sendMessage(String message) throws IOException {
		return RPCExec_.sendMessage(message);
	}

	@Override
	public String getMessages() throws IOException {
		return RPCExec_.getMessages();
	}
	
	@Override
	public void disconnect() throws IOException
	{
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
			ClientApp.Instance().finishConnectionState(true);
		} catch (IOException e) {
			ClientApp.Instance().finishConnectionState(false);
			e.printStackTrace();
		}
	}

	@Override
	public void connect() 
	{
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
			ClientApp.Instance().finishConnectionState(true);
		} catch (UnknownHostException e) {
			ClientApp.Instance().finishConnectionState(false);
			e.printStackTrace();
		} catch (IOException e) {
			ClientApp.Instance().finishConnectionState(false);
			e.printStackTrace();
		}
		transport_ = new HttpJsonRpcClientTransport(socket_, input_, output_);
		JsonRpcInvoker invoker = new JsonRpcInvoker();
		RPCExec_ = invoker.get(transport_, "RPCExec", Client_ChatRoom.class);
		ClientApp.Instance().finishConnectionState(true);
	}

}
