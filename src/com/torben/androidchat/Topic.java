package com.torben.androidchat;

import java.util.List;

public class Topic {
	private String name_;
	public List<String> userList;
	public List<String> messages;
	
	public Topic (String n)
	{
		name_ = n;
	}
	
	public String getName(){
		return name_;
	}
}
