package com.torben.androidchat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.torben.androidchat.JSONRPC.client.HttpJsonRpcClientTransport;
import com.torben.androidchat.JSONRPC.client.JsonRpcInvoker;

public class Client_ChatRoom_RPC implements Client_ChatRoom {

    private String host_;
    private HttpJsonRpcClientTransport transport_;
    private Client_ChatRoom RPCExec_;
	
	public Client_ChatRoom_RPC (String host) throws IOException
	{
		host_ = host;
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

	@SuppressWarnings("unchecked")
	@Override
	public void connect() 
	{
		try {
			transport_ = new HttpJsonRpcClientTransport(new URL(host_));
			JsonRpcInvoker invoker = new JsonRpcInvoker();
			RPCExec_ = invoker.get(transport_, "RPCExec", Client_ChatRoom.class);
			Client.Instance().finishConnectionState(true);
		} catch (MalformedURLException e) {
			Client.Instance().finishConnectionState(false);
			e.printStackTrace();
		}
		
	}


}
