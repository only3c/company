package com.example.company.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuTreeDTO {
	private String id;

	private String text;

	private List<MenuTreeDTO> children = new ArrayList<MenuTreeDTO>();
	private String state;

	private Integer menulevel;

	public Integer getMenulevel() {
		return menulevel;
	}

	public void setMenulevel(Integer menulevel) {
		this.menulevel = menulevel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<MenuTreeDTO> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTreeDTO> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
