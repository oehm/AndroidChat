/*
 * Copyright (C) 2011 ritwik.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.torben.androidchat.JSONRPC.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

import android.os.SystemClock;
import android.provider.Settings.System;
import android.util.Log;

public class HttpJsonRpcClientTransport{

	Socket socket_;

	private BufferedReader input_;
	private BufferedWriter output_;

	private final long TimeoutTime = 3000;
	
    public HttpJsonRpcClientTransport(Socket socket, BufferedReader reader, BufferedWriter writer) {
        socket_ = socket;
        input_ = reader;
        output_ = writer;
    }
	
	public String threadedCall(final String requestData)
	{
		long time = SystemClock.elapsedRealtime();
		final StringBuilder sb = new StringBuilder();
		sb.append("!");
		if(requestData != null)
		{
			Thread th = new Thread()
			{
				@Override
				public void run()
				{
					try {
						String r = call(requestData);
						sb.delete(0,1);
						sb.append(r);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			th.start();
			while(sb.toString().equals("!"));
			{
				if(SystemClock.elapsedRealtime() - time > TimeoutTime)
				{
					Log.v("TRANSPORT","timed out post!");
					th.interrupt();
					return sb.toString();
				}
			}
			return sb.toString();
		}
		else
		{
			Thread th =new Thread()
			{
				@Override
				public void run()
				{
					try {
						Log.v("THREADREAD", "start reading!");
						String r = read();
						Log.v("THREADREAD", "end reading!");
						sb.delete(0,1);
						sb.append(r);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			th.start();
			while(sb.toString().equals("!"));
			{
				if(SystemClock.elapsedRealtime() - time > TimeoutTime)
				{
					Log.v("TRANSPORT","timed out get!");
					th.interrupt();
					return sb.toString();
				}
			}
			return sb.toString();
		}
	}
	
    private final String call(String requestData) throws Exception, IOException {
    	String responseData = post(requestData);
        return responseData;
    }

    private String post(String data) throws IOException {
    	output_.write(data);
    	//output_.newLine();
        output_.flush();
        Log.v("Transport", "Sended data: "+ data);
        return read();
    }
    
    private String read() throws IOException
    {
    	String input = null;
    	long time = SystemClock.elapsedRealtime();
    	while(input== null)
    	{
    		if(input_.ready())
    			input = input_.readLine();
    		if(SystemClock.elapsedRealtime() - time > TimeoutTime)
    			input = "FAIL!";
    	}
    	Log.v("Transport", "Recived data: "+input);
    	return input;
    }
}
