package com.torben.androidchat;

import java.io.IOException;
import java.util.List;

public class ChatRoom_Host {

	private static ChatRoom_Host instance_ = null;
	
	public static ChatRoom_Host Instance(){
		if(instance_ == null){
			instance_ = new ChatRoom_Host();
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
				for(int i = t.messages.size()-11; i<t.messages.size();i++){
					result += t.messages.get(i) +";;";
				}
				return result;
			}
		}
		return null;
	}
}
