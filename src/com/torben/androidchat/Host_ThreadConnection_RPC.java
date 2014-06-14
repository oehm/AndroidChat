package com.torben.androidchat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.util.Log;

import com.torben.androidchat.JSONRPC.client.HttpJsonRpcClientTransport;
import com.torben.androidchat.JSONRPC.server.JsonRpcExecutor;

public class Host_ThreadConnection_RPC implements Client_ChatRoom, Runnable{

	 
	private final JsonRpcExecutor executor_;
	 
	private Socket socket_;
	private BufferedReader input_;
	private BufferedWriter output_;
	
	private HttpJsonRpcClientTransport transpot_;

	public Host_ThreadConnection_RPC(Socket client) throws IOException {
		socket_ = client;
		input_ = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
		output_ = new BufferedWriter(new OutputStreamWriter(socket_.getOutputStream()));
		transpot_ = new HttpJsonRpcClientTransport(socket_, input_, output_);
		executor_ = bind();
	}

	@SuppressWarnings("unchecked")
	private JsonRpcExecutor bind() {
		JsonRpcExecutor executor_ = new JsonRpcExecutor();
        //Client_ChatRoom clientImpl = new Host_RPC_Executor();
        executor_.addHandler("RPCExec",this,Client_ChatRoom.class);
        return executor_;
    }

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			executor_.execute(transpot_);
		}
		try {
			socket_.close();
			output_.close();
			input_.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean addParticipant(String name) throws IOException {
		Log.v("Host", name);
		return false;
	}

	@Override
	public boolean removeParticipant(String name) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean joinTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leaveTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendMessage(String message) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMessages() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnect() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connect() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
