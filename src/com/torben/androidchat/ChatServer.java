package com.torben.androidchat;

import java.io.IOException;

public interface ChatServer {
	// Connects to an implementation of a chat room.
	public void connect(String host, int port) throws IOException;
	// Disconnects from the chat room server.
	public void disconnect() throws IOException;
	// Return the chat room which is operated by that server. Before getChatRoom
	// can be invoked, connect must be called. getChatRoom must have singleton
	// semantics, i.e. it should return the same instance upon subsequent calls.
	// After disconnect has been called getChatRoom returns null.
	public ChatRoom getChatRoom();
}
