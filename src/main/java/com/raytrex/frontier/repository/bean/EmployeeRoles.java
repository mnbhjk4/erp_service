package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="employee_roles")
public class EmployeeRoles implements Serializable{
	
	@Id
	@Column(name="role_id")
	@Expose
	private String roleId;
	
	@Column(name="from_date")
	@Expose
	private Date fromDate;
	
	@Column(name="to_date")
	@Expose
	private Date toDate;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="role_id")
	@Expose
	private Role role;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	@Expose(deserialize=false,serialize=false)
	private Employee employee;
	

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

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
}
