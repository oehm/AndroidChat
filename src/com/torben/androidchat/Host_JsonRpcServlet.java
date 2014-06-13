package com.torben.androidchat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.torben.androidchat.JSONRPC.server.JsonRpcExecutor;
import com.torben.androidchat.JSONRPC.server.JsonRpcServletTransport;

public class Host_JsonRpcServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private JsonRpcExecutor executor_;

    public Host_JsonRpcServlet() {
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

}