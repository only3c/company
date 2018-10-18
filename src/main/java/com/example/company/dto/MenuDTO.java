package com.example.company.dto;

import java.io.Serializable;

public class MenuDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String menucode;

	private String menuname;

	private String url;

	private String parentcode;

	private String parentname;

	private Integer menulevel;

	private String isleaf;

	private String isleafname;

	private Integer disporder;

	private String instclass;
	private String instclassname;

	private String description;

	private String opinstcode;
	private String opinstname;

	private String oprcode;

	private String oprname;

	private String opdate;

	public String getMenucode() {
		return menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParentcode() {
		return parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}

	public Integer getMenulevel() {
		return menulevel;
	}

	public void setMenulevel(Integer menulevel) {
		this.menulevel = menulevel;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public String getIsleafname() {
		return isleafname;
	}

	public void setIsleafname(String isleafname) {
		this.isleafname = isleafname;
	}

	public Integer getDisporder() {
		return disporder;
	}

	public void setDisporder(Integer disporder) {
		this.disporder = disporder;
	}

	public String getInstclass() {
		return instclass;
	}

	public void setInstclass(String instclass) {
		this.instclass = instclass;
	}

	public String getInstclassname() {
		return instclassname;
	}

	public void setInstclassname(String instclassname) {
		this.instclassname = instclassname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOpinstcode() {
		return opinstcode;
	}

	public void setOpinstcode(String opinstcode) {
		this.opinstcode = opinstcode;
	}

	public String getOpinstname() {
		return opinstname;
	}

	public void setOpinstname(String opinstname) {
		this.opinstname = opinstname;
	}

	public String getOprcode() {
		return oprcode;
	}

	public void setOprcode(String oprcode) {
		this.oprcode = oprcode;
	}

	public String getOprname() {
		return oprname;
	}

	public void setOprname(String oprname) {
		this.oprname = oprname;
	}

	public String getOpdate() {
		return opdate;
	}

	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}
	
	
}