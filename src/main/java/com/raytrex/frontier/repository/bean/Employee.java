package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Employee implements Serializable{
	@Id
	private String uid;
	
	@Column(name="emp_no")
	private String empNo;
	private String mail;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="uid")
	private EmployeeInfo employeesInfo;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	@JoinColumn(name="uid")
	private List<EmployeeRoles> roles;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public EmployeeInfo getEmployeesInfo() {
		return employeesInfo;
	}

	public void setEmployeesInfo(EmployeeInfo employeesInfo) {
		this.employeesInfo = employeesInfo;
	}

//	public List<EmployeeRoles> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(List<EmployeeRoles> roles) {
//		this.roles = roles;
//	}
}
