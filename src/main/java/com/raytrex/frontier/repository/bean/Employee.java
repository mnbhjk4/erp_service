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
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="employee")
public class Employee implements Serializable{
	@Id
	@Expose
	private String uid;
	
	@Column(name="emp_no")
	@Expose
	private String empNo;
	
	@Expose
	private String mail;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="uid")
	@Expose
	private EmployeeInfo employeesInfo;
	
	@Expose
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="uid")
	private List<EmployeeRoles> roleList;

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

	public List<EmployeeRoles> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<EmployeeRoles> roleList) {
		this.roleList = roleList;
	}
	
}
