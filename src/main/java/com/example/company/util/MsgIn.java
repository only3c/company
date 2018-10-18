package com.example.company.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class MsgIn<T> {
	private String requestcode;
	private String requestmessage;
	private Boolean flage;
	private Map<String, Object> data=new HashMap<String, Object>();
	private String jsonstring;
	private T tobj;
	private String tjson;

	public MsgIn(T bob,String reqcode,String reqmessage){
		tobj=bob;
		requestcode=reqcode;
		tjson=JSON.toJSONString(bob);
		requestmessage=reqmessage;
		data.put(MsgHeadUtils.RESPONSECCODE, reqcode);
		data.put(MsgHeadUtils.RESPONSSSAGE, reqmessage);
		data.put(MsgHeadUtils.RESPONBODY, bob);
		jsonstring=JSON.toJSONString(data,SerializerFeature.DisableCircularReferenceDetect);
		
	}
	public MsgIn(T bob){
		tobj=bob;
		requestcode="0000";
		requestmessage="查询成功";
		data.put(MsgHeadUtils.RESPONSECCODE, requestcode);
		data.put(MsgHeadUtils.RESPONSSSAGE, requestmessage);
		data.put(MsgHeadUtils.RESPONBODY, bob);
		jsonstring=JSON.toJSONString(data,SerializerFeature.DisableCircularReferenceDetect);
		
	}
	public MsgIn(String reqcode,String reqmessage){
		requestcode=reqcode;
		requestmessage=reqmessage;
		data.put(MsgHeadUtils.RESPONSECCODE, reqcode);
		data.put(MsgHeadUtils.RESPONSSSAGE, reqmessage);
		jsonstring=JSON.toJSONString(data,SerializerFeature.DisableCircularReferenceDetect);
		
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
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
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
	public String getTjson() {
		return tjson;
	}
	public void setTjson(String tjson) {
		this.tjson = tjson;
	}
}
