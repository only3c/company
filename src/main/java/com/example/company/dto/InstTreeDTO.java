package com.example.company.dto;

import java.util.ArrayList;
import java.util.List;

public class InstTreeDTO {  
    private String id;  // 部门树ID
       
    private String text;  // 部门树显示名称
       
    private List<InstTreeDTO> children = new ArrayList<InstTreeDTO>();  // 子节点
       
    private String state; // 部门节点状态（closed-不自动展开该节点；open-展开节点）
    
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the children
	 */
	public List<InstTreeDTO> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<InstTreeDTO> children) {
		this.children = children;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

}
