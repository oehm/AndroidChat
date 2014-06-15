package com.torben.androidchat;

import java.io.IOException;

import android.util.Log;

public class Host_RPC_Executor implements Client_ChatRoom {

	private String name_ = null;
	private String topic_ = null;
	
	@Override
	public boolean addParticipant(String name) throws IOException {
		name_ = name;
		return true;
	}

	@Override
	public boolean removeParticipant(String name) throws IOException {
		if(topic_ == null) return false;
		return Host_ChatRoom.Instance().removeParticipant(name, topic_);
	}

	@Override
	public boolean addTopic(String topic) throws IOException {
		return Host_ChatRoom.Instance().addTopic(topic);
	}

	@Override
	public boolean removeTopic(String topic) throws IOException {
		return Host_ChatRoom.Instance().removeTopic(topic);
	}

	@Override
	public boolean joinTopic(String topic) throws IOException {
		if(name_ == null) return false;
		if(Host_ChatRoom.Instance().addParticipant(name_, topic))
		{
			topic_ = topic;
			return true;
		}
		return false;
	}

	@Override
	public boolean leaveTopic(String topic) throws IOException {
		if(name_ == null) return false;
		return Host_ChatRoom.Instance().removeParticipant(name_, topic);
	}

	@Override
	public boolean sendMessage(String message) throws IOException {
		if(name_ == null || topic_ == null)return false;
		return Host_ChatRoom.Instance().sendMessage(name_, topic_, message);
	}

	@Override
	public String getMessages() throws IOException {
		if(topic_ == null) return null;
		return Host_ChatRoom.Instance().getMessages(topic_);
	}

	@Override
	public void disconnect() throws IOException {}
	@Override
	public void connect() throws IOException {}

}
