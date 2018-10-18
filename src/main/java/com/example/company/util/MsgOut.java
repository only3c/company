package com.example.company.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;


public class   MsgOut<T> {
	private String requestcode;
	private String requestmessage;
	private Boolean flage;
	private Map<Object, Object> data;
	private String jsonstring;
	private T tobj;
	private String tjson;

	@SuppressWarnings("unchecked")
	public MsgOut(String jsons){
		this.jsonstring = jsons;
		data=JSON.parseObject(jsonstring, Map.class);
		if(data==null){
			
		}else{
			requestcode=(String)data.get(MsgHeadUtils.RESPONSECCODE);
			requestmessage=(String)data.get(MsgHeadUtils.RESPONSSSAGE);
			tobj=(T)data.get(MsgHeadUtils.RESPONBODY);
			tjson=JSON.toJSONString(tobj);
		}
		
		if("0000".equals(requestcode)){
			flage=true;
		}else{
			flage=false;
		}
		
		
	}
	@SuppressWarnings("unchecked")
	public MsgOut(String jsons,Class<T> tt){
		this.jsonstring = jsons;
		data=JSON.parseObject(jsonstring, Map.class);
		if(data==null){
			
		}else{
			requestcode=(String)data.get(MsgHeadUtils.RESPONSECCODE);
			requestmessage=(String)data.get(MsgHeadUtils.RESPONSSSAGE);
			Object tto=data.get(MsgHeadUtils.RESPONBODY);
			tjson=JSON.toJSONString(tto);
			tobj=JSON.parseObject(tjson, tt);
		}
		
		if("0000".equals(requestcode)){
			flage=true;
		}else{
			flage=false;
		}
	}
	
	public String getTjson() {
		return tjson;
	}
	public void setTjson(String tjson) {
		this.tjson = tjson;
	}
	public String getRequestcode() {
		return requestcode;
	}
	public void setRequestcode(String requestcode) {
		this.requestcode = requestcode;
	}
	public String getRequestmessage() {
		return requestmessage;
	}
	public void setRequestmessage(String requestmessage) {
		this.requestmessage = requestmessage;
	}
	public Boolean getFlage() {
		return flage;
	}
	public void setFlage(Boolean flage) {
		this.flage = flage;
	}
	public Map<Object, Object> getData() {
		return data;
	}
	public void setData(Map<Object, Object> data) {
		this.data = data;
	}
	public String getJsonstring() {
		return jsonstring;
	}
	public void setJsonstring(String jsonstring) {
		this.jsonstring = jsonstring;
	}
	public T getTobj() {
		return tobj;
	}
	public void setTobj(T tobj) {
		this.tobj = tobj;
	}
}
