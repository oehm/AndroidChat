package com.torben.androidchat;

import java.util.ArrayList;
import java.util.List;

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
