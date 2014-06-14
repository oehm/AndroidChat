package com.torben.androidchat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Host_ChatRoom {

	private Host_ChatRoom()
	{
		topics_ = new ArrayList<Host_ChatRoom.Topic>();
	}
	
	private static Host_ChatRoom instance_ = null;
	
	public static Host_ChatRoom Instance(){
		if(instance_ == null){
			instance_ = new Host_ChatRoom();
		}
		return instance_;
	}

	private List<Topic> topics_;
	
	public boolean addParticipant(String name, String topic) throws IOException {
		// TODO Auto-generated method stub
		for (Topic t : topics_) {
			if(t.getName().equals(topic)){
				if (t.userList.contains(name)) {
					return false;
				}
				t.userList.add(name);
				return true;
			}
		}
		return false;
	}

	public boolean removeParticipant(String name, String topic)
			throws IOException {
		// TODO Auto-generated method stub
		for (Topic t : topics_) {
			if(t.getName().equals(topic)){
				if (t.userList.contains(name)) {
					t.userList.remove(name);
					return true;
				}
				return false;
			}
		}
		return false;
	}

	public boolean addTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		for (Topic t : topics_) {
			if(t.getName().equals(topic)){
				return false;
			}
		}
		topics_.add(new Topic(topic));
		
		return true;
	}

	public boolean removeTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		for (Topic t : topics_) {
			if(t.getName().equals(topic)){
				topics_.remove(t);
				return true;
			}
		}
		
		return false;
	}

	public boolean sendMessage(String name, String topic, String message) throws IOException {
		// TODO Auto-generated method stub
		for (Topic t : topics_) {
			if(t.getName().equals(topic)){
				if (t.userList.contains(name)) {
					t.messages.add(name +": " + message);
					return true;
				}
			}
		}
		return false;
	}

	public String getMessages(String topic) throws IOException {
		// TODO Auto-generated method stub
		for (Topic t : topics_) {
			if(t.getName().equals(topic)){
				String result = "";
				//int startIndex =t.messages.size()-11;
				//if(startIndex < 0) startIndex = 0;
				for(int i = 0; i<t.messages.size();i++){
					result += t.messages.get(i) +";;";
				}
				return result;
			}
		}
		return null;
	}
	
	public class Topic {
		private String name_;
		public List<String> userList;
		public List<String> messages;
		
		public Topic (String n)
		{
			name_ = n;
			userList = new ArrayList<String>();
			messages = new ArrayList<String>();
		}
		
		public String getName(){
			return name_;
		}
	}
}
