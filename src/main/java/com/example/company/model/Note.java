package com.example.company.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Note {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String notename;
	private String usercode;
	private String username;
	
	private String instcode;
	private String instname;
	
	private long meetingid;
	private String meetingname;
	
	private String updatetime;
	
	@Column(length=10000)
	private String notes;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getMeetingid() {
		return meetingid;
	}

	public void setMeetingid(long meetingid) {
		this.meetingid = meetingid;
	}

	public String getMeetingname() {
		return meetingname;
	}

	public void setMeetingname(String meetingname) {
		this.meetingname = meetingname;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotename() {
		return notename;
	}

	public void setNotename(String notename) {
		this.notename = notename;
	}
	
	
}
