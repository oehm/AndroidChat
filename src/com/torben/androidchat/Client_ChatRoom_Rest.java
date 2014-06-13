package com.torben.androidchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;

public class Client_ChatRoom_Rest extends AsyncTask<String, String, String>  implements Client_ChatRoom {

	private URL url_;
	private HttpURLConnection urlConnection_;
    private OutputStreamWriter output_;
    private BufferedReader input_;
    StringBuilder sb = new StringBuilder();
    
    JSONObject jsonOut = null;
    JSONObject jsonIn = null;
	
	public Client_ChatRoom_Rest(String host) throws IOException
	{
		url_ = new URL(host);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		boolean read = params[0] != null;
		
		if(jsonOut!= null)
		{
			try {
				output_.write(jsonOut.toString());
				output_.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!read) return  null;
		
			StringBuilder input = new StringBuilder();
			String line;
			try {
				while((line = input_.readLine()) != null)
				 {
			        // Append server response in string
			        input.append(line);
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				jsonIn = new JSONObject(input.toString());
			} catch (JSONException e) {
				e.printStackTrace();
				return null;

			}
		try {
			return jsonIn.getString("value");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean addParticipant(String name) throws IOException {
		// TODO Auto-generated method stub
		jsonOut = new JSONObject();
		try {
			jsonOut.put("Command", "name");
			jsonOut.put("value", name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.execute((String[])null);
		return false;
	}

	@Override
	public boolean removeParticipant(String name) throws IOException {
		// TODO Auto-generated method stub
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("Command", "remove_name");
			jsonOut.put("value", name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.execute((String[])null);
		return false;
	}

	@Override
	public boolean addTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("Command", "add_topic");
			jsonOut.put("value", topic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.execute((String[])null);
		return false;
	}

	@Override
	public boolean removeTopic(String topic) throws IOException {
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("Command", "remove_topic");
			jsonOut.put("value", topic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.execute((String[])null);
		return false;
	}

	@Override
	public boolean joinTopic(String topic) throws IOException {
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("Command", "join_topic");
			jsonOut.put("value", topic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.execute((String[])null);
		return false;
	}

	@Override
	public boolean leaveTopic(String topic) throws IOException {
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("Command", "leave_topic");
			jsonOut.put("value", topic);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.execute((String[])null);
		return false;
	}

	@Override
	public boolean sendMessage(String message) throws IOException {
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("Command", "add_topic");
			jsonOut.put("value", message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.execute((String[])null);
		return false;
	}

	@Override
	public String getMessages() throws IOException {
		JSONObject jsonOut = new JSONObject();
		try {
			jsonOut.put("Command", "action");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] args = new String[1];
		args[0] = "return";
		AsyncTask<String, String, String> task = this.execute(args);	
		
		try {
			return task.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void disconnect() throws IOException
	{
		output_.close();
		input_.close();
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
			urlConnection_.disconnect();
			Client.Instance().finishConnectionState(true);
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
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
			urlConnection_ = (HttpURLConnection) url_.openConnection();
			output_= new OutputStreamWriter(urlConnection_.getOutputStream());
			input_ = new BufferedReader(new InputStreamReader(urlConnection_.getInputStream()));
			Client.Instance().finishConnectionState(true);
		} catch (IOException e) {
			Client.Instance().finishConnectionState(false);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
