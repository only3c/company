package com.example.company.dto;

public class ExamDTO {

	private long id;
	
	private String examname;
	
	private String timelength;
	
	private String fullmarks;
	
	private String passingmark;
	
	private int questionnum;
	
	private Boolean hasquestion; 

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getExamname() {
		return examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public String getTimelength() {
		return timelength;
	}

	public void setTimelength(String timelength) {
		this.timelength = timelength;
	}

	public String getFullmarks() {
		return fullmarks;
	}

	public void setFullmarks(String fullmarks) {
		this.fullmarks = fullmarks;
	}

	public String getPassingmark() {
		return passingmark;
	}

	public void setPassingmark(String passingmark) {
		this.passingmark = passingmark;
	}

	public int getQuestionnum() {
		return questionnum;
	}

	public void setQuestionnum(int questionnum) {
		this.questionnum = questionnum;
	}

	public Boolean getHasquestion() {
		return hasquestion;
	}

	public void setHasquestion(Boolean hasquestion) {
		this.hasquestion = hasquestion;
	}

	
}
