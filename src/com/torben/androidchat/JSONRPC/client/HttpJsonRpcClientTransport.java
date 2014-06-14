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

import android.os.AsyncTask;
import android.util.Log;

import com.torben.androidchat.JSONRPC.commons.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class HttpJsonRpcClientTransport extends AsyncTask<String, Integer, String> implements JsonRpcClientTransport {

    private URL url;
    private final Map<String, String> headers;

    public HttpJsonRpcClientTransport(URL url) {
        this.url = url;
        this.headers = new HashMap<String, String>();
    }

    public final void setHeader(String key, String value) {
        this.headers.put(key, value);
    }

	@Override
	protected String doInBackground(String... params) {
		try {
			return call(params[0]);
		} catch (IOException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}
    
    protected void onProgressUpdate(Integer... progress) {
        
    }

    protected void onPostExecute(Long result) {
        
    }

	
	
    public final String call(String requestData) throws Exception, IOException {
        String responseData = post(url, headers, requestData);
        return responseData;
    }

    private String post(URL url, Map<String, String> headers, String data) throws IOException {
    	
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.addRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        connection.addRequestProperty("Accept-Encoding", "gzip");
        connection.setRequestMethod("Post");
        connection.setDoOutput(true);
        //connection.setChunkedStreamingMode(0); //myCode
        Log.v("RPC", "try to connect!");
        connection.connect();  //error here
        Log.v("RPC", "connected!");
        OutputStream out = null;
        
        try {
        	out = connection.getOutputStream();
        	out.write(data.getBytes());
            out.flush();
            out.close();

            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
            	throw new JsonRpcClientException("unexpected status code returned : " + statusCode);
            }
        } finally {
            if (out != null) {            	
            	out.close();
            }
        }

        Log.v("RPC_POST", "still working2");
        
        String responseEncoding = connection.getHeaderField("Content-Encoding");
        responseEncoding = (responseEncoding == null ? "" : responseEncoding.trim());

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        InputStream in = connection.getInputStream();
        try {
            in = connection.getInputStream();
            if ("gzip".equalsIgnoreCase(responseEncoding)) {
                in = new GZIPInputStream(in);
            }
            in = new BufferedInputStream(in);

            byte[] buff = new byte[1024];
            int n;
            while ((n = in.read(buff)) > 0) {
                bos.write(buff, 0, n);
            }
            bos.flush();
            bos.close();
        } finally {
            if (in != null) {
                in.close();
            }
        }        
        return bos.toString();
    }
}