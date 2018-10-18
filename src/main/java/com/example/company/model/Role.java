package com.example.company.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Id
	@NotNull
	@Column(name="rolecode",nullable=false,unique=true)
    private String rolecode;

    private String rolename;

    private String instclass;
    private String instclassname;

    private String description;

    @ManyToMany
    private List<Menu> menus;
  
    private String opinstcode;
    private String opinstname;


    private String oprcode;
    private String oprname;

    private String opdate;


	public String getRolecode() {
		return rolecode;
	}


	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}


	public String getRolename() {
		return rolename;
	}


	public void setRolename(String rolename) {
		this.rolename = rolename;
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


	public List<Menu> getMenus() {
		return menus;
	}


	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}



}