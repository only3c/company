package com.example.company.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class InstInfo implements Serializable {
	
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
	@Column(name="instcode",nullable=false,unique=true)
    private String instcode;

    private String instname;
    //简称
    private String instsname;

    private String telno;

    private String address;
    
    private String oprcode;

    private String opdate;
    //负责人
    private String contactname;
    //联系电话
    private String contacttelno;
    /**
     * 父部门信息
     */
    @ManyToOne
    private InstInfo pinstinfo;
    
    /**
     * 省 数据字典
     */
    private String province;
    private String provincename;
    
    /**
     * 市数据字典
     */
    private String city;
    private String cityname;
    
    /**
     * 部门类别字典
     */
    private String instclass;
    private String instclassname;
    
    /**
     * 部门状态 字典
     */
    private String instflag;
    private String instflagname;

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

	public String getInstflag() {
		return instflag;
	}

	public void setInstflag(String instflag) {
		this.instflag = instflag;
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

	public InstInfo getPinstinfo() {
		return pinstinfo;
	}

	public void setPinstinfo(InstInfo pinstinfo) {
		this.pinstinfo = pinstinfo;
	}

//	public List<InstInfo> getCinstinfos() {
//		return cinstinfos;
//	}
//
//	public void setCinstinfos(List<InstInfo> cinstinfos) {
//		this.cinstinfos = cinstinfos;
//	}

	public String getInstclass() {
		return instclass;
	}

	public void setInstclass(String instclass) {
		this.instclass = instclass;
	}

	public String getInstflagname() {
		return instflagname;
	}

	public void setInstflagname(String instflagname) {
		this.instflagname = instflagname;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getInstclassname() {
		return instclassname;
	}

	public void setInstclassname(String instclassname) {
		this.instclassname = instclassname;
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

	public String getInstsname() {
		return instsname;
	}

	public void setInstsname(String instsname) {
		this.instsname = instsname;
	}
}