package com.torben.androidchat;

import java.io.IOException;

public interface ChatRoom {
	// Add a participant who joined the chat room.
	public boolean addParticipant (String name) throws IOException;
	// Remove a participant who left the chat room.
	public boolean removeParticipant(String name) throws IOException;
	
	// Add a topic to the chat room.
	public boolean addTopic(String topic) throws IOException;
	// Remove a topic from the chat room.
	public boolean removeTopic(String topic) throws IOException;
	
	// Add a topic to the chat room.
	public boolean joinTopic(String topic) throws IOException;
	// Remove a topic from the chat room.
	public boolean leaveTopic(String topic) throws IOException;
	// Sends a message to the chat room.
	public boolean sendMessage(String message) throws IOException;
	// Get last ten messages from the chat room.
	public String getMessages() throws IOException;
	
	public void disconnect() throws IOException;
	public void connect() throws IOException;
	public boolean isConnected();
}
