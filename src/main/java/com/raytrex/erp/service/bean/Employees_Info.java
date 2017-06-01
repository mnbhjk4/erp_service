package com.raytrex.erp.service.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Employees_Info implements Serializable{
	@Id
	private String uid;
	private String first_name;
	private String mid_name;
	private String last_name;
	private Date birth_date;
	private String gender;
	private String contact_addr_1;
	private String contact_addr_2;
	private String contact_phone_1;
	private String contact_phone_2;
	private Date hire_date;
	private Date leave_date;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getMid_name() {
		return mid_name;
	}
	public void setMid_name(String mid_name) {
		this.mid_name = mid_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public Date getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getContact_addr_1() {
		return contact_addr_1;
	}
	public void setContact_addr_1(String contact_addr_1) {
		this.contact_addr_1 = contact_addr_1;
	}
	public String getContact_addr_2() {
		return contact_addr_2;
	}
	public void setContact_addr_2(String contact_addr_2) {
		this.contact_addr_2 = contact_addr_2;
	}
	public String getContact_phone_1() {
		return contact_phone_1;
	}
	public void setContact_phone_1(String contact_phone_1) {
		this.contact_phone_1 = contact_phone_1;
	}
	public String getContact_phone_2() {
		return contact_phone_2;
	}
	public void setContact_phone_2(String contact_phone_2) {
		this.contact_phone_2 = contact_phone_2;
	}
	public Date getHire_date() {
		return hire_date;
	}
	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}
	public Date getLeave_date() {
		return leave_date;
	}
	public void setLeave_date(Date leave_date) {
		this.leave_date = leave_date;
	}
	
	
}
