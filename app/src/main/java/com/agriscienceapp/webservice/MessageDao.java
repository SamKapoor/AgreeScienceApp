package com.agriscienceapp.webservice;

import com.agriscienceapp.model.MessageListVo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MessageDao implements MessageListner {
	private ArrayList<MessageListVo> arrMessageListVo = new ArrayList<MessageListVo>();

	@Override
	public ArrayList<MessageListVo> getAllUsersFromJson(JSONArray jsonArrayResult) {
		JSONObject jobj;

		for (int i = 0; i < jsonArrayResult.length(); i++) {
			try {
				MessageListVo messageListVo = new MessageListVo();

				jobj = jsonArrayResult.getJSONObject(i);
				if(jobj.has("NewsId"))
					messageListVo.setRefId(jobj.getString("NewsId"));
				if(jobj.has("NewsTitle"))
					messageListVo.setNewsTitle(jobj.getString("NewsTitle"));
//				if(jobj.has(WebField.GET_MESSAGE.RESPONSE_MESSAGE))
//					messageListVo.setTxtMessage(jobj.getString(WebField.GET_MESSAGE.RESPONSE_MESSAGE));
//				if(jobj.has(WebField.GET_MESSAGE.RESPONSE_DATE_TIME))
//					messageListVo.setTxtDayAndTime(jobj.getString(WebField.GET_MESSAGE.RESPONSE_DATE_TIME));

				arrMessageListVo.add(messageListVo);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return arrMessageListVo;

	}

}
