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

import com.google.gson.annotations.Expose;

@Entity
@Table(name="task_status")
public class TaskStatus implements Serializable {
	public static String NotAction = "Not Action";
	public static String Progressing = "Progressing";
	public static String Done = "Done";
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="task_status_id")
	@Expose
	private Integer taskStatusId;
	
	@Column(name="task_no")
	@Expose
	private String taskNo;
	
	@Column(name="update_time")
	@Expose
	private Timestamp updateTime;
	
	@Expose
	private String status;
	
	@Expose
	private Integer priority = 6;
	
	@Column(name="start_date")
	@Expose
	private Timestamp startDate;
	
	@Column(name="due_date")
	@Expose
	private Timestamp dueDate;
	
	@Column(name="alert_Date")
	@Expose
	private Timestamp alertDate;
	
	@Column(name="end_date")
	@Expose
	private Timestamp endDate;
	
	@Column(name="task_index")
	@Expose
	private Integer taskIndex;
	
	@Column(name="parent_task_no")
	@Expose
	private String parentTaskNo;
	
	@Column(name="description")
	@Expose
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

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getDueDate() {
		return dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}

	public Timestamp getAlertDate() {
		return alertDate;
	}

	public void setAlertDate(Timestamp alertDate) {
		this.alertDate = alertDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
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
