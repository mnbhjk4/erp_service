package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="task")
public class Task implements Serializable{
	@Column(name="project_number")
	@Expose
	private String projectNumber;
	
	@Id
	@Column(name="task_no")
	@Expose
	private String taskNo;
	

	
	@Column(name="customer_id")
	@Expose
	private String customerId;
	
	@Expose
	private String name;
	
	@Column(name="attach_uuid")
	@Expose
	private String attachUuid;
	
	@Column(name="permission_id")
	@Expose
	private String permissionId;
	
	@Column(name="parent_task_no")
	@Expose
	private String parentTaskNo;
	
	@Column(name="type")
	@Expose
	private String type;
	
	@OneToMany(cascade={CascadeType.MERGE})
	@JoinColumn(name="task_no")
	@OrderBy("join_date")
	@Expose
	private List<TaskOwner> taskOwnerList = new ArrayList<TaskOwner>();
	
	@OneToMany(cascade={CascadeType.MERGE})
	@JoinColumn(name="task_no")
	@OrderBy("comment_date DESC")
	@Expose
	private List<TaskComment> taskCommentList = new ArrayList<TaskComment>();
	
	@OneToMany(cascade={CascadeType.MERGE})
	@JoinColumn(name="task_no")
	@OrderBy("update_time DESC")
	@Expose
	private List<TaskStatus> taskStatusList = new ArrayList<TaskStatus>();
	
	@Transient
	@Expose
	private List<Task> subTaskList = new ArrayList<Task>();
	
	@Transient
	@Expose
	private Quotation quotation;
	
	@Transient
	@Expose
	private List<QuotationItem> quotationItemList;
	
	public List<Task> getSubTaskList() {
		return subTaskList;
	}
	public void setSubTaskList(List<Task> subTaskList) {
		this.subTaskList = subTaskList;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Quotation getQuotation() {
		return quotation;
	}
	public void setQuotation(Quotation quotation) {
		this.quotation = quotation;
	}
	public List<QuotationItem> getQuotationItemList() {
		return quotationItemList;
	}
	public void setQuotationItemList(List<QuotationItem> quotationItemList) {
		this.quotationItemList = quotationItemList;
	}
	
}
