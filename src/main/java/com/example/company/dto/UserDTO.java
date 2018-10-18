package com.example.company.dto;

import java.util.List;

public class UserDTO {

	private String usercode;

	private String username;

	private String password;

	private String certtype;

	private String certno;

	private String telno;

	private String mobilephone;

	private String email;

	private String qq;

	private String address;

	private String postcode;

	private String instcode;

	private String userflag;

	private String opinstcode;

	private String oprcode;

	private String opdate;

	private InstDTO inst;

	private List<RoleDTO> roles;

	private List<MenuDTO> menus;

	private String instname;

	private String userflagname;

	private String roleName;

	public String getInstname() {
		return instname;
	}

	public void setInstname(String instname) {
		this.instname = instname;
	}

	public String getUserflagame() {
		return userflagname;
	}

	public void setUserflagname(String userflagname) {
		this.userflagname = userflagname;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	public InstDTO getInst() {
		return inst;
	}

	public void setInst(InstDTO inst) {
		this.inst = inst;
	}

	public List<MenuDTO> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuDTO> menus) {
		this.menus = menus;
	}

	private static final long serialVersionUID = 1L;

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode == null ? null : usercode.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getCerttype() {
		return certtype;
	}

	public void setCerttype(String certtype) {
		this.certtype = certtype == null ? null : certtype.trim();
	}

	public String getCertno() {
		return certno;
	}

	public void setCertno(String certno) {
		this.certno = certno == null ? null : certno.trim();
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno == null ? null : telno.trim();
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone == null ? null : mobilephone.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq == null ? null : qq.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode == null ? null : postcode.trim();
	}

	public String getInstcode() {
		return instcode;
	}

	public void setInstcode(String instcode) {
		this.instcode = instcode == null ? null : instcode.trim();
	}

	public String getUserflag() {
		return userflag;
	}

	public void setUserflag(String userflag) {
		this.userflag = userflag == null ? null : userflag.trim();
	}

	public String getOpinstcode() {
		return opinstcode;
	}

	public void setOpinstcode(String opinstcode) {
		this.opinstcode = opinstcode == null ? null : opinstcode.trim();
	}

	public String getOprcode() {
		return oprcode;
	}

	public void setOprcode(String oprcode) {
		this.oprcode = oprcode == null ? null : oprcode.trim();
	}

	public String getOpdate() {
		return opdate;
	}

	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", usercode=").append(usercode);
		sb.append(", username=").append(username);
		sb.append(", password=").append(password);
		sb.append(", certtype=").append(certtype);
		sb.append(", certno=").append(certno);
		sb.append(", telno=").append(telno);
		sb.append(", mobilephone=").append(mobilephone);
		sb.append(", email=").append(email);
		sb.append(", qq=").append(qq);
		sb.append(", address=").append(address);
		sb.append(", postcode=").append(postcode);
		sb.append(", instcode=").append(instcode);
		sb.append(", userflag=").append(userflag);
		sb.append(", opinstcode=").append(opinstcode);
		sb.append(", oprcode=").append(oprcode);
		sb.append(", opdate=").append(opdate);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}

}
