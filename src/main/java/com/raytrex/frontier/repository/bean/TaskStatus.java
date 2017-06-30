package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="task_status")
public class TaskStatus implements Serializable {
	public static String NotAction = "Not Action";
	public static String Progressing = "Progressing";
	public static String Done = "Done";
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="task_status_id")
	private Integer taskStatusId;
	
	@Column(name="task_no")
	private String taskNo;
	
	@Column(name="update_time")
	private Timestamp updateTime;
	
	private String status;
	
	private Integer priority = 6;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="due_date")
	private Date dueDate;
	
	@Column(name="alert_Date")
	private Date alertDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="task_index")
	private Integer taskIndex;
	
	@Column(name="parent_task_no")
	private String parentTaskNo;
	
	@Column(name="description")
	private String description;
	
	public Integer getTaskStatusId() {
		return taskStatusId;
	}

	public void setTaskStatusId(Integer taskStatusId) {
		this.taskStatusId = taskStatusId;
	}

	
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getAlertDate() {
		return alertDate;
	}

	public void setAlertDate(Date alertDate) {
		this.alertDate = alertDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getTaskIndex() {
		return taskIndex;
	}

	public void setTaskIndex(Integer taskIndex) {
		this.taskIndex = taskIndex;
	}

	public String getParentTaskNo() {
		return parentTaskNo;
	}

	public void setParentTaskNo(String parentTaskNo) {
		this.parentTaskNo = parentTaskNo;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@PrePersist
	public void onCreate(){
		this.updateTime = new Timestamp(System.currentTimeMillis());
	}
	
	@PreUpdate
	public void onUpdate(){
		this.updateTime = new Timestamp(System.currentTimeMillis());
	}
}
