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

public class Host_ThreadConnection_RPC implements Runnable{

	 
	private final JsonRpcExecutor executor_;
	
	private Host_RPC_Executor remoteObj_; 
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
        remoteObj_ = new Host_RPC_Executor();
		executor_.addHandler("RPCExec",remoteObj_,Client_ChatRoom.class);
        return executor_;
    }

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			executor_.execute(transpot_);
		}
		try {
			Log.v("RPC", "ClientDisconnect!");
			socket_.close();
			output_.close();
			input_.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
