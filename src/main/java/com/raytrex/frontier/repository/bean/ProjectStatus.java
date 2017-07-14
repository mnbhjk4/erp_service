package com.raytrex.frontier.repository.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="project_status")
public class ProjectStatus {
	@Id
	@Column(name="status_uuid")
	@Expose
	private String statusUuid;
	
	@Column(name="project_no")
	@Expose
	private String projectNo;
	
	@Column(name="project_name")
	@Expose
	private String projectName;
	
	@Column(name="start_date")
	@Expose
	private Date startDate;
	
	@Column(name="end_date")
	@Expose
	private Date endDate;
	
	@Column(name="update_date")
	@Expose
	private Date updateDate;
	
	@Column(name="due_date")
	@Expose
	private Date dueDate;
	
	@Column(name="alarm_date")
	@Expose
	private Date alarmDate;
	
	@Expose
	private String description;

	@Expose
	private Integer priority = 6;
	
	public String getStatusUuid() {
		return statusUuid;
	}

	public void setStatusUuid(String statusUuid) {
		this.statusUuid = statusUuid;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getAlarmDate() {
		return alarmDate;
	}

	public void setAlarmDate(Date alarmDate) {
		this.alarmDate = alarmDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	@PrePersist
	public void onCreate(){
		if(this.updateDate == null){
			this.updateDate = new Date();
		}
	}
	
	@PreUpdate
	public void onUpdate(){
		if(this.updateDate == null){
			this.updateDate = new Date();
		}
	}
	
}
