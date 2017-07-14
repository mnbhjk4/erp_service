package com.raytrex.frontier.repository.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Permission implements Serializable, GrantedAuthority {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="permission_serial")
	private Integer permissionSerial;
	
	@Column(name="permission_id")
	private String permissionId;
	@Column(name="role_id")
	private String roleId;
	private String uid;
	
	@Column(name="function_name")
	private String fucntionName;
	
	private Integer create;
	private Integer update;
	private Integer read;
	private Integer delete;
	
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public Integer getCreate() {
		return create;
	}
	public void setCreate(Integer create) {
		this.create = create;
	}
	public Integer getUpdate() {
		return update;
	}
	public void setUpdate(Integer update) {
		this.update = update;
	}
	public Integer getRead() {
		return read;
	}
	public void setRead(Integer read) {
		this.read = read;
	}
	public Integer getDelete() {
		return delete;
	}
	public void setDelete(Integer delete) {
		this.delete = delete;
	}
	
	public Integer getPermissionSerial() {
		return permissionSerial;
	}
	public void setPermissionSerial(Integer permissionSerial) {
		this.permissionSerial = permissionSerial;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getFucntionName() {
		return fucntionName;
	}
	public void setFucntionName(String fucntionName) {
		this.fucntionName = fucntionName;
	}
	@Override
	public String getAuthority() {
		String curd = "";
		if(create != null && create >=  0 && create <= 9){
			curd+=create.toString();
		}
		if(update != null && update >=  0 && update <= 9){
			curd+=update.toString();
		}
		if(read != null && read >=  0 && read <= 9){
			curd+=read.toString();
		}
		if(delete != null && delete >=  0 && delete <= 9){
			curd+=delete.toString();
		}
		return curd+"_"+this.getFucntionName();
	}
}
