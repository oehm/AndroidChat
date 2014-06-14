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

import android.os.AsyncTask;

public class HttpJsonRpcClientTransport extends AsyncTask<String, Integer, String> implements JsonRpcClientTransport {

	Socket socket_;

	private BufferedReader input_;
	private BufferedWriter output_;

    public HttpJsonRpcClientTransport(Socket socket, BufferedReader reader, BufferedWriter writer) {
        socket_ = socket;
        input_ = reader;
        output_ = writer;
    }

	@Override
	protected String doInBackground(String... params) {
		if(params != null)
		{
			try {
				return call(params[0]);
			} catch (IOException e) {
				return null;
			} catch (Exception e) {
				return null;
			}
		}
		else
		{
			try {
				return read();
			} catch (IOException e) {
				return null;
			}
		}
	}
	
    public final String call(String requestData) throws Exception, IOException {
        String responseData = post(requestData);
        return responseData;
    }

    private String post(String data) throws IOException {
        
    	output_.write(data);
    	output_.newLine();
        output_.flush();
        output_.close();
        
        return input_.readLine();
    }
    
    private String read() throws IOException
    {
    	String input =null;
		input = input_.readLine();
    	return input;
    }
}
