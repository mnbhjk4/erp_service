package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Employees implements Serializable{
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	private String uid;
	
	@Column(name="emp_no")
	private String empNo;
	private String mail;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="uid")
	private Employees_Info employees_Info;
	
	@OneToMany
	@JoinColumn(name="uid")
	private List<Employees_Roles> roles;

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

	public Employees_Info getEmployees_Info() {
		return employees_Info;
	}

	public void setEmployees_Info(Employees_Info employees_Info) {
		this.employees_Info = employees_Info;
	}

	public List<Employees_Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Employees_Roles> roles) {
		this.roles = roles;
	}
}