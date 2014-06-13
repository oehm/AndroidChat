package com.torben.androidchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.alexd.jsonrpc.JSONRPCParams.Versions;
import org.json.JSONException;
import org.json.JSONObject;

public class Client_ChatRoom_RPC implements Client_ChatRoom {

    StringBuilder sb = new StringBuilder();

    private JSONRPCClient client_;
    private String host_;
	
	public Client_ChatRoom_RPC (String host) throws IOException
	{
		host_ = host;
	}
	
	
	
	@Override
	public boolean addParticipant(String name) throws IOException {
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("value", name);
			client_.call("addParticipant", jsonOut);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JSONRPCException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return false;
	}

	@Override
	public boolean removeParticipant(String name) throws IOException {
		// TODO Auto-generated method stub
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("value", name);
			client_.call("removeParticipant", jsonOut);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JSONRPCException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("value", topic);
			client_.call("addTopic", jsonOut);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JSONRPCException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeTopic(String topic) throws IOException {
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("value", topic);
			client_.call("removeTopic", jsonOut);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean joinTopic(String topic) throws IOException {
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("value", topic);
			client_.call("joinTopic", jsonOut);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean leaveTopic(String topic) throws IOException {
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("value", topic);
			client_.call("leaveTopic",jsonOut);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean sendMessage(String message) throws IOException {
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("value", message);
			client_.call("sendMessage", jsonOut);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return false;
	}

	@Override
	public String getMessages() throws IOException {
		return null;
	}
	
	@Override
	public void disconnect() throws IOException
	{
	}

	@Override
	public void connect() 
	{
		client_ = JSONRPCClient.create(host_,Versions.VERSION_2);
		client_.setConnectionTimeout(2000);
		client_.setSoTimeout(2000);
		
	}


}
