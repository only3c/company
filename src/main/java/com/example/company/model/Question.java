package com.example.company.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private Long examid;
	
	private String examname;
	
	private String quesubject;
	
	private String queone;
	
	private String quetwo;
	
	private String quethree;
	
	private String quefour; 
	
	private String rightque;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getExamid() {
		return examid;
	}

	public void setExamid(Long examid) {
		this.examid = examid;
	}

	public String getQuesubject() {
		return quesubject;
	}

	public void setQuesubject(String quesubject) {
		this.quesubject = quesubject;
	}

	public String getQueone() {
		return queone;
	}

	public void setQueone(String queone) {
		this.queone = queone;
	}

	public String getQuetwo() {
		return quetwo;
	}

	public void setQuetwo(String quetwo) {
		this.quetwo = quetwo;
	}

	public String getQuethree() {
		return quethree;
	}

	public void setQuethree(String quethree) {
		this.quethree = quethree;
	}

	public String getQuefour() {
		return quefour;
	}

	public void setQuefour(String quefour) {
		this.quefour = quefour;
	}

	public String getRightque() {
		return rightque;
	}

	public void setRightque(String rightque) {
		this.rightque = rightque;
	}

	public String getExamname() {
		return examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}
	
	
}
