package com.example.company.dto;

public class MeetingDTO {

	private long id;
	
	private String subject;
	
	private String usercode;
	private String username;
	
	private String instcode;
	private String instname;
	
	private String meetingtype;
	private String meetingtypename;

	private Double timelength;
	
	private String starttime;
	
	private String endtime;
	private String meetingroom;
	private String summary;
	
	private String flowtype;
	private String flowtypename;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getInstcode() {
		return instcode;
	}
	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}
	public String getInstname() {
		return instname;
	}
	public void setInstname(String instname) {
		this.instname = instname;
	}
	public String getMeetingtype() {
		return meetingtype;
	}
	public void setMeetingtype(String meetingtype) {
		this.meetingtype = meetingtype;
	}
	public String getMeetingtypename() {
		return meetingtypename;
	}
	public void setMeetingtypename(String meetingtypename) {
		this.meetingtypename = meetingtypename;
	}
	public Double getTimelength() {
		return timelength;
	}
	public void setTimelength(Double timelength) {
		this.timelength = timelength;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getFlowtype() {
		return flowtype;
	}
	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}
	public String getFlowtypename() {
		return flowtypename;
	}
	public void setFlowtypename(String flowtypename) {
		this.flowtypename = flowtypename;
	}
	public String getMeetingroom() {
		return meetingroom;
	}
	public void setMeetingroom(String meetingroom) {
		this.meetingroom = meetingroom;
	}
	
	
}
