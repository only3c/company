package com.example.company.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.company.model.InstInfo;
import com.example.company.model.Menu;
import com.example.company.model.Role;

/**
 * 登陆用户信息
 */
public class LoginUserDTO {
	
	/**
	 * 用户代码
	 */
	private String usercode; 
	
	/**
	 * 用户名称
	 */
	private String username;
	
	/**
	 * 用户状态
	 */
	private String userflag;
	
	/**
	 * 部门代码
	 */
	private String instcode; 
	
	/**
	 * 部门名称
	 */
	private String instName;

	/**
	 * 角色信息
	 */
	private List<Role> roles = new ArrayList<Role>();
	
	/**
	 * 菜单信息
	 */
	private List<Menu> menus = new ArrayList<Menu>();

	/**
	 * 部门信息
	 */
	private InstInfo inst;
	
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

	public String getUserflag() {
		return userflag;
	}

	public void setUserflag(String userflag) {
		this.userflag = userflag;
	}

	public String getInstcode() {
		return instcode;
	}

	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public InstInfo getInst() {
		return inst;
	}

	public void setInst(InstInfo inst) {
		this.inst = inst;
	}

}
