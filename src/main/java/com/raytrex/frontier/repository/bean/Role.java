package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="role")
public class Role implements Serializable{

	@Column(name="dep_id",insertable=false,updatable=false)
	@Expose
	private String depId;
	
	@Id
	@Column(name="role_id")
	@Expose
	private String roleId;

	@Column(name="role_name")
	@Expose
	private String roleName;
	
	@Column(name="role_level")
	@Expose
	private String roleLevel;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="dep_id")
	@Expose
	private Department department;
	
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id")
	private EmployeeRoles employeeRoles;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id")
	@Expose
	private List<Permission> permissionList = new ArrayList<Permission>();

	
	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public EmployeeRoles getEmployeeRoles() {
		return employeeRoles;
	}

	public void setEmployeeRoles(EmployeeRoles employeeRoles) {
		this.employeeRoles = employeeRoles;
	}

	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}
	
	
}
