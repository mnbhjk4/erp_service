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
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="permission")
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
	private String create = "F";
	
	@Column(name="update_p")
	@Expose
	private String update = "F";
	
	@Column(name="read_p")
	@Expose
	private String read = "F";
	
	@Column(name="delete_p")
	@Expose
	private String delete = "F";
	

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
	public String getCreate() {
		return create;
	}
	public void setCreate(String create) {
		this.create = create;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
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
		if(create != null && Integer.parseInt(create,16) >=  0 && Integer.parseInt(create,16) <= 9){
			curd+=create.toString();
		}
		if(update != null && Integer.parseInt(update,16) >=  0 &&  Integer.parseInt(update,16) <= 9){
			curd+=update.toString();
		}
		if(read != null && Integer.parseInt(read,16) >=  0 && Integer.parseInt(read,16) <= 9){
			curd+=read.toString();
		}
		if(delete != null && Integer.parseInt(delete,16) >=  0 && Integer.parseInt(delete,16) <= 9){
			curd+=delete.toString();
		}
		return curd+"_"+this.getFunctionName();
	}
}
