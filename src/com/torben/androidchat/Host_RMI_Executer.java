package com.torben.androidchat;

import java.io.IOException;

public interface Host_RMI_Executer {
	public boolean addParticipant(String name, String topic) throws IOException;
	public boolean removeParticipant(String name, String topic)throws IOException;
	public boolean addTopic(String topic) throws IOException;
	public boolean removeTopic(String topic) throws IOException;
	public boolean sendMessage(String name, String topic, String message) throws IOException;
	public String getMessages(String topic) throws IOException;
}
