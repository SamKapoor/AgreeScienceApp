package com.agriscienceapp.webservice;

import com.agriscienceapp.model.MessageListVo;

import java.util.ArrayList;

import org.json.JSONArray;


public interface MessageListner
{	
	ArrayList<MessageListVo> getAllUsersFromJson(JSONArray jsonArrayResult);
}
