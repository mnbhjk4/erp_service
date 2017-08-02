package com.raytrex.frontier.repository.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;


@Entity
@Table(name="project")
public class Project {
	@Id
	@Column(name="project_no")
	@Expose
	private String projectNo;
	
	@Column(name="customer_id")
	@Expose
	private String customerId;
	
	@Column(name="attach_uuid")
	@Expose
	private String attachUuid;
	
	@Column(name="permission_id")
	@Expose
	private String permissionId;
	
	@Expose
	@OneToMany(mappedBy="project")
	private List<ProjectOwner> ownerList;
	
	@Expose
	@OneToMany(mappedBy="project")
	@OrderBy("update_date DESC")
	private List<ProjectStatus> statusList;
	
	

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAttachUuid() {
		return attachUuid;
	}

	public void setAttachUuid(String attachUuid) {
		this.attachUuid = attachUuid;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public List<ProjectOwner> getOwnerList() {
		return ownerList;
	}

	public void setOwnerList(List<ProjectOwner> ownerList) {
		this.ownerList = ownerList;
	}

	public List<ProjectStatus> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<ProjectStatus> statusList) {
		this.statusList = statusList;
	}

	
	
	
}
