package com.raytrex.erp.service.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Task implements Serializable{
	@Column(name="project_number")
	private String projectNumber;
	
	@Id
	@Column(name="task_no")
	private String taskNo;
	
	@Column(name="customer_id")
	private String customerId;
	
	private String name;
	
	private String description;
	
	@Column(name="attach_uuid")
	private String attachUuid;
	
	@Column(name="permission_id")
	private String permissionId;
	
	@Column(name="parent_task_no")
	private String parentTaskNo;
	
	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name="task_no")
	private List<Task_Owner> taskOwnerList = new ArrayList<Task_Owner>();
	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name="task_no")
	private List<Task_Comment> taskCommentList = new ArrayList<Task_Comment>();
	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name="task_no")
	private List<Task_Status> taskStatusList = new ArrayList<Task_Status>();
	
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getParentTaskNo() {
		return parentTaskNo;
	}
	public void setParentTaskNo(String parentTaskNo) {
		this.parentTaskNo = parentTaskNo;
	}
	public List<Task_Owner> getTaskOwnerList() {
		return taskOwnerList;
	}
	public void setTaskOwnerList(List<Task_Owner> taskOwnerList) {
		this.taskOwnerList = taskOwnerList;
	}
	public List<Task_Comment> getTaskCommentList() {
		return taskCommentList;
	}
	public void setTaskCommentList(List<Task_Comment> taskCommentList) {
		this.taskCommentList = taskCommentList;
	}
	public List<Task_Status> getTaskStatusList() {
		return taskStatusList;
	}
	public void setTaskStatusList(List<Task_Status> taskStatusList) {
		this.taskStatusList = taskStatusList;
	}
}
