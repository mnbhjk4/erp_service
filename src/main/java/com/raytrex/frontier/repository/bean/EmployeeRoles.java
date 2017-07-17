package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="employee_roles")
public class EmployeeRoles implements Serializable{
	@Id
	@Column(name="e_index")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String eIndex;
	
	@Column(name="uid")
	@Expose
	private String uid;
	
	@Expose
	@Column(name="from_date")
	private Date fromDate;
	
	@Expose
	@Column(name="to_date")
	private Date toDate;
	
	
	@Expose
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id")
	private Role role;
	


//	public String getRoleId() {
//		return roleId;
//	}
//
//	public void setRoleId(String roleId) {
//		this.roleId = roleId;
//	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String geteIndex() {
		return eIndex;
	}

	public void seteIndex(String eIndex) {
		this.eIndex = eIndex;
	}
	
}
