package com.raytrex.frontier.repository.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class Project {
	@Id
	@Column(name="project_no")
	private String projectNo;
	
	@Column(name="customer_id")
	private String customerId;
	
	@Column(name="attach_uuid")
	private String attachUuid;
	
	@Column(name="permission_id")
	private String permissionId;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="project_no")
	private List<ProjectOwner> ownerList = new ArrayList<ProjectOwner>();
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="project_no")
	private List<ProjectStatus> statusList = new ArrayList<ProjectStatus>();
	
	

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
