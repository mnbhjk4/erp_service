package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;


@Entity
@Table(name="employee_info")
public class EmployeeInfo implements Serializable{
	@Id
	@Expose
	private String uid;
	@Column(name="first_name")
	@Expose
	private String firstName;
	
	@Column(name="mid_name")
	@Expose
	private String midName;
	
	@Column(name="last_name")
	@Expose
	private String lastName;
	
	@Column(name="birth_date")
	@Expose
	private Date birthDate;
	
	@Column(name="preferred_language")
	@Expose
	private String preferredLanguage = "zh_TW";
	
	@Expose
	private String gender;
	
	@Column(name="contact_addr_1")
	@Expose
	private String contactAddr1;
	
	@Column(name="contact_addr_2")
	@Expose
	private String contactAddr2;
	
	@Column(name="contact_phone_1")
	@Expose
	private String contactPhone1;
	
	@Column(name="contact_phone_2")
	@Expose
	private String contactPhone2;
	
	@Column(name="hire_date")
	@Expose
	private Date hireDate;
	
	@Column(name="leave_date")
	@Expose
	private Date leaveDate;

	@Lob
	@Column(name="image")
	@Expose
	private byte[] image;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMidName() {
		return midName;
	}

	public void setMidName(String midName) {
		this.midName = midName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContactAddr1() {
		return contactAddr1;
	}

	public void setContactAddr1(String contactAddr1) {
		this.contactAddr1 = contactAddr1;
	}

	public String getContactAddr2() {
		return contactAddr2;
	}

	public void setContactAddr2(String contactAddr2) {
		this.contactAddr2 = contactAddr2;
	}

	public String getContactPhone1() {
		return contactPhone1;
	}

	public void setContactPhone1(String contactPhone1) {
		this.contactPhone1 = contactPhone1;
	}

	public String getContactPhone2() {
		return contactPhone2;
	}

	public void setContactPhone2(String contactPhone2) {
		this.contactPhone2 = contactPhone2;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	
}
