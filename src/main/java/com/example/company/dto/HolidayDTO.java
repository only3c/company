package com.example.company.dto;

public class HolidayDTO {

	private long id;
	
	private String usercode;
	private String username;
	
	private String instcode;
	private String instname;
	
	private String holidaytype;
	private String holidaytypename;

	private Double timelength;
	
	private String starttime;
	
	private String endtime;
	
	private String cause;
	
	private String flowtype;
	private String flowtypename;
	
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

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFlowtype() {
		return flowtype;
	}

	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}

	public String getHolidaytype() {
		return holidaytype;
	}

	public void setHolidaytype(String holidaytype) {
		this.holidaytype = holidaytype;
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

	public String getHolidaytypename() {
		return holidaytypename;
	}

	public void setHolidaytypename(String holidaytypename) {
		this.holidaytypename = holidaytypename;
	}

	public String getFlowtypename() {
		return flowtypename;
	}

	public void setFlowtypename(String flowtypename) {
		this.flowtypename = flowtypename;
	}
	
	
}
