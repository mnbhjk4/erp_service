package com.raytrex.frontier.repository.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

import com.google.gson.annotations.Expose;

@Entity
public class Permission implements Serializable, GrantedAuthority {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="permission_serial")
	@Expose
	private Integer permissionSerial;
	
	@Column(name="permission_id")
	@Expose
	private String permissionId;
	@Column(name="role_id")
	@Expose
	private String roleId;
	@Expose
	private String uid;
	
	@Column(name="function_name")
	@Expose
	private String functionName;
	
	@Column(name="create_p")
	@Expose
	private Integer create = 999;
	
	@Column(name="update_p")
	@Expose
	private Integer update = 999;
	
	@Column(name="read_p")
	@Expose
	private Integer read = 999;
	
	@Column(name="delete_p")
	@Expose
	private Integer delete = 999;
	

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
	
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
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
		return curd+"_"+this.getFunctionName();
	}
}
