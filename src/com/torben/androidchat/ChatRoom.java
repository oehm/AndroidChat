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
	// Sends a message to the chat room.
	public boolean sendMessage(String topic, String message) throws IOException;
	// Get last ten messages from the chat room.
	public String getMessages(String topic) throws IOException;
	// Refresh last ten messages from the chat room; refresh participants and topics, too.
	public String refreshMessages(String topic) throws IOException;
}
