package com.torben.androidchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class ChatRoom_Rest extends AsyncTask<String, Void, Void>  implements ChatRoom {

	private URL url_;
	private HttpURLConnection urlConnection_;
    private OutputStreamWriter output_;
    private BufferedReader intput_;
    StringBuilder sb = new StringBuilder();
    
    JSONObject jsonOut = null;
    JSONObject jsonIn = null;

    private boolean connected_ = false;
	
	public ChatRoom_Rest(String host) throws IOException
	{
		url_ = new URL(host);
		urlConnection_ = (HttpURLConnection) url_.openConnection();
		output_= new OutputStreamWriter(urlConnection_.getOutputStream());
		intput_ = new BufferedReader(new InputStreamReader(urlConnection_.getInputStream()));
	}
	
	@Override
	protected Void doInBackground(String... params) {
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
		
		if(read)
		{
			StringBuilder input = new StringBuilder();
			String line;
			try {
				while((line = intput_.readLine()) != null)
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
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
	public void disconnect() throws IOException
	{
		output_.close();
		intput_.close();
		urlConnection_.disconnect();
	}

	@Override
	public void connect() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isConnected() {
		return connected_;
	}

}
