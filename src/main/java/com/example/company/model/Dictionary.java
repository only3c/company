package com.example.company.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
   	@NotNull
   	@Column(nullable=false)
    private Long id;
    
    private String datatype;
//    @Id
//   	@NotNull
//   	@Column(nullable=false)
    private String datacode;
    
    private String datatypename;


    private String dataname;


    private String parentcode;


    private Integer displayorder;


	public String getDatacode() {
		return datacode;
	}


	public void setDatacode(String datacode) {
		this.datacode = datacode;
	}



	public String getDataname() {
		return dataname;
	}


	public void setDataname(String dataname) {
		this.dataname = dataname;
	}



	public Integer getDisplayorder() {
		return displayorder;
	}


	public void setDisplayorder(Integer displayorder) {
		this.displayorder = displayorder;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDatatype() {
		return datatype;
	}


	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}


	public String getDatatypename() {
		return datatypename;
	}


	public void setDatatypename(String datatypename) {
		this.datatypename = datatypename;
	}


	public String getParentcode() {
		return parentcode;
	}


	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}
	
	
}