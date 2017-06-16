package com.raytrex.frontier.repository.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
}
