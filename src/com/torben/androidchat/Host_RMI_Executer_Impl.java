package com.torben.androidchat;

import java.io.IOException;

public class Host_RMI_Executer_Impl implements Host_RMI_Executer {

	@Override
	public boolean addParticipant(String name, String topic) throws IOException {
		// TODO Auto-generated method stub
		return Host_ChatRoom.Instance().addParticipant(name, topic);
	}

	@Override
	public boolean removeParticipant(String name, String topic)
			throws IOException {
		// TODO Auto-generated method stub
		return Host_ChatRoom.Instance().removeParticipant(name, topic);
	}

	@Override
	public boolean addTopic(String topic) throws IOException {
		// TODO Auto-generated method stub		
		return Host_ChatRoom.Instance().addTopic(topic);
	}

	@Override
	public boolean removeTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		return Host_ChatRoom.Instance().removeTopic(topic);
	}

	@Override
	public boolean sendMessage(String name, String topic, String message)
			throws IOException {
		// TODO Auto-generated method stub
		return Host_ChatRoom.Instance().sendMessage(name, topic, message);
	}

	@Override
	public String getMessages(String topic) throws IOException {
		// TODO Auto-generated method stub
		return Host_ChatRoom.Instance().getMessages(topic);
	}

}
