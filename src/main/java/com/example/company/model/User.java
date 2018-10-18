package com.example.company.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@NotNull
	@Column(name="usercode",nullable=false,unique=true)
    private String usercode;

    private String username;

    private String password;

    private String certtype;
    
    private String certtypename;
    
    private String certno;

    private String telno;

    private String mobilephone;

    private String email;

    private String qq;

    private String address;
    
    private String postcode;
    
    /**
     * 用户状态
     */
    private String userflag;
    /**
     * 用户状态 中文
     */
    private String userflagname;
    
    private String oprcode;

    private String opdate;
    
    /**
     * 部门
     */
    @ManyToOne
    private InstInfo inst;
    
    private String instname;
    private String rolename;

    /**
     * 角色
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Role> roles;
    
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCerttype() {
		return certtype;
	}

	public void setCerttype(String certtype) {
		this.certtype = certtype;
	}

	public String getCerttypename() {
		return certtypename;
	}

	public void setCerttypename(String certtypename) {
		this.certtypename = certtypename;
	}

	public String getCertno() {
		return certno;
	}

	public void setCertno(String certno) {
		this.certno = certno;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserflag() {
		return userflag;
	}

	public void setUserflag(String userflag) {
		this.userflag = userflag;
	}

	public String getUserflagname() {
		return userflagname;
	}

	public void setUserflagname(String userflagname) {
		this.userflagname = userflagname;
	}

	public String getOprcode() {
		return oprcode;
	}

	public void setOprcode(String oprcode) {
		this.oprcode = oprcode;
	}

	public String getOpdate() {
		return opdate;
	}

	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}

	public InstInfo getInst() {
		return inst;
	}

	public void setInst(InstInfo inst) {
		this.inst = inst;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getInstname() {
		return instname;
	}

	public void setInstname(String instname) {
		this.instname = instname;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
    
}