package com.example.company.dto;

import java.io.Serializable;

public class InstDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private String instcode;

    private String instname;

    private String instsname;

    private String pinstcode;

    private String finstcode;

    private String binstcode;

    private String telno;

    private String address;

    private String province;

    private String city;

    private String instclass;
    
    private String instclassname;

    private String serviceurl;

    private String hpfauthurl;

    private String contactname;

    private String contacttelno;

    private String instflag;

    private String validbegindate;

    private String validenddate;

    private Integer delaydays;

    private String factvaliddate;

    private String isauthor;

    private String isauthcom;
    
    private String opinstcode;

    private String oprcode;

    private String opdate;


    /**
     * 父部门信息
     */
    private InstDTO pinstinfo;


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


	public String getInstsname() {
		return instsname;
	}


	public void setInstsname(String instsname) {
		this.instsname = instsname;
	}


	public String getPinstcode() {
		return pinstcode;
	}


	public void setPinstcode(String pinstcode) {
		this.pinstcode = pinstcode;
	}


	public String getFinstcode() {
		return finstcode;
	}


	public void setFinstcode(String finstcode) {
		this.finstcode = finstcode;
	}


	public String getBinstcode() {
		return binstcode;
	}


	public void setBinstcode(String binstcode) {
		this.binstcode = binstcode;
	}


	public String getTelno() {
		return telno;
	}


	public void setTelno(String telno) {
		this.telno = telno;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
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


	public String getServiceurl() {
		return serviceurl;
	}


	public void setServiceurl(String serviceurl) {
		this.serviceurl = serviceurl;
	}


	public String getHpfauthurl() {
		return hpfauthurl;
	}


	public void setHpfauthurl(String hpfauthurl) {
		this.hpfauthurl = hpfauthurl;
	}


	public String getContactname() {
		return contactname;
	}


	public void setContactname(String contactname) {
		this.contactname = contactname;
	}


	public String getContacttelno() {
		return contacttelno;
	}


	public void setContacttelno(String contacttelno) {
		this.contacttelno = contacttelno;
	}


	public String getInstflag() {
		return instflag;
	}


	public void setInstflag(String instflag) {
		this.instflag = instflag;
	}


	public String getValidbegindate() {
		return validbegindate;
	}


	public void setValidbegindate(String validbegindate) {
		this.validbegindate = validbegindate;
	}


	public String getValidenddate() {
		return validenddate;
	}


	public void setValidenddate(String validenddate) {
		this.validenddate = validenddate;
	}


	public Integer getDelaydays() {
		return delaydays;
	}


	public void setDelaydays(Integer delaydays) {
		this.delaydays = delaydays;
	}


	public String getFactvaliddate() {
		return factvaliddate;
	}


	public void setFactvaliddate(String factvaliddate) {
		this.factvaliddate = factvaliddate;
	}


	public String getIsauthor() {
		return isauthor;
	}


	public void setIsauthor(String isauthor) {
		this.isauthor = isauthor;
	}


	public String getIsauthcom() {
		return isauthcom;
	}


	public void setIsauthcom(String isauthcom) {
		this.isauthcom = isauthcom;
	}


	public String getOpinstcode() {
		return opinstcode;
	}


	public void setOpinstcode(String opinstcode) {
		this.opinstcode = opinstcode;
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


	public InstDTO getPinstinfo() {
		return pinstinfo;
	}


	public void setPinstinfo(InstDTO pinstinfo) {
		this.pinstinfo = pinstinfo;
	}
    
    
}