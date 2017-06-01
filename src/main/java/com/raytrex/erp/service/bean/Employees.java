package com.raytrex.erp.service.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	private String emp_no;
	private String mail;
	@OneToOne
	@JoinColumn(name="uid")
	private Employees_Info employees_Info;
	
	@OneToMany
	@JoinTable(
			name="Employee_Role",
			joinColumns=@JoinColumn(name="uid"),
			inverseJoinColumns=@JoinColumn(name="role_id")
	)
	private List<Role> roles;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
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
}
