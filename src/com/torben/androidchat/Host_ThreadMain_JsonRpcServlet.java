package com.torben.androidchat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.torben.androidchat.JSONRPC.server.JsonRpcExecutor;
import com.torben.androidchat.JSONRPC.server.JsonRpcServletTransport;

public class Host_ThreadMain_JsonRpcServlet extends HttpServlet implements Runnable {

	private static final long serialVersionUID = 1L;
	private final JsonRpcExecutor executor_;

    public Host_ThreadMain_JsonRpcServlet() throws ServletException {
        executor_ = bind();
    }

    @SuppressWarnings("unchecked")
	private JsonRpcExecutor bind() {
        JsonRpcExecutor executor_ = new JsonRpcExecutor();
        
        Client_ChatRoom clientImpl = new Host_RPC_Executor();
        executor_.addHandler("RPCExec",clientImpl,Client_ChatRoom.class);
        // add more services here

        return executor_;
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        executor_.execute(new JsonRpcServletTransport(req, resp));
    }

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
		}
		
		}

}