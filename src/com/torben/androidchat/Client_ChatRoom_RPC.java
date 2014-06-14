package com.torben.androidchat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.torben.androidchat.JSONRPC.client.HttpJsonRpcClientTransport;
import com.torben.androidchat.JSONRPC.client.JsonRpcInvoker;

public class Client_ChatRoom_RPC implements Client_ChatRoom {

    private String host_;
    private int port_;
    
    
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
	}

	@Override
	public void connect() 
	{
		//String url = "http://127.0.0.1:8080/jsonrpc"
		String url = "http://"+host_+":"+port_;
		try{	
			transport_ = new HttpJsonRpcClientTransport(new URL(url));
			JsonRpcInvoker invoker = new JsonRpcInvoker();
			RPCExec_ = invoker.get(transport_, "RPCExec", Client_ChatRoom.class);
			Client.Instance().finishConnectionState(true);
		} catch (MalformedURLException e) {
			Client.Instance().finishConnectionState(false);
			e.printStackTrace();
		}
		
	}


}
