package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

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
	
	@Column(name="attach_uuid")
	private String attachUuid;
	
	@Column(name="permission_id")
	private String permissionId;
	
	@Column(name="parent_task_no")
	private String parentTaskNo;
	
	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name="task_no")
	@OrderBy("join_date")
	private List<TaskOwner> taskOwnerList = new ArrayList<TaskOwner>();
	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name="task_no")
	@OrderBy("comment_date DESC")
	private List<TaskComment> taskCommentList = new ArrayList<TaskComment>();
	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name="task_no")
	@OrderBy("update_time DESC")
	private List<TaskStatus> taskStatusList = new ArrayList<TaskStatus>();
	
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
	public List<TaskOwner> getTaskOwnerList() {
		return taskOwnerList;
	}
	public void setTaskOwnerList(List<TaskOwner> taskOwnerList) {
		this.taskOwnerList = taskOwnerList;
	}
	
	public List<TaskComment> getTaskCommentList() {
		return taskCommentList;
	}
	public void setTaskCommentList(List<TaskComment> taskCommentList) {
		this.taskCommentList = taskCommentList;
	}
	public List<TaskStatus> getTaskStatusList() {
		return taskStatusList;
	}
	public void setTaskStatusList(List<TaskStatus> taskStatusList) {
		this.taskStatusList = taskStatusList;
	}
}
