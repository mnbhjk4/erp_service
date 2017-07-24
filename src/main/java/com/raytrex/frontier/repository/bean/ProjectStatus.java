package com.raytrex.frontier.repository.bean;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="project_status")
public class ProjectStatus {
	@Id
	@Column(name="status_uuid")
	@Expose
	private String statusUuid;
	
	@Transient
	@Expose
	private String projectNo;
	
	@Column(name="project_name")
	@Expose
	private String projectName;
	
	@Column(name="start_date")
	@Expose
	private Timestamp startDate;
	
	@Column(name="end_date")
	@Expose
	private Timestamp endDate;
	
	@Column(name="update_date")
	@Expose
	private Timestamp updateDate;
	
	@Column(name="due_date")
	@Expose
	private Timestamp dueDate;
	
	@Column(name="alarm_date")
	@Expose
	private Timestamp alarmDate;
	
	@Expose
	private String status;
	
	@Expose
	private String description;

	@Expose
	private Integer priority = 6;
	
	@ManyToOne
	@JoinColumn(name="project_no")
	private Project project;
	

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

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

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}

	public Timestamp getAlarmDate() {
		return alarmDate;
	}

	public void setAlarmDate(Timestamp alarmDate) {
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
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@PrePersist
	public void onCreate(){
		if(this.updateDate == null){
			this.updateDate = new Timestamp(System.currentTimeMillis());
		}
	}
	
	@PreUpdate
	public void onUpdate(){
		if(this.updateDate == null){
			this.updateDate = new Timestamp(System.currentTimeMillis());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ProjectStatus){
			ProjectStatus ps = (ProjectStatus)obj;
			if(!this.projectName.equals(ps.getProjectName())){
				return false;
			}else if(this.startDate == null && ps.getStartDate() != null){
				return false;
			}else if(this.getStartDate() != null && ps.getStartDate() != null &&( this.getStartDate().getTime() != ps.getStartDate().getTime())){
				return false;
			}else if(this.dueDate == null && ps.getDueDate() != null){
				return false;
			}else if(this.getDueDate() != null && ps.getDueDate() != null &&( this.getDueDate().getTime() != ps.getDueDate().getTime())){
				return false;
			}else if(this.endDate == null && ps.getEndDate() != null){
				return false;
			}else if(this.getEndDate() != null && ps.getEndDate() != null &&( this.getEndDate().getTime() != ps.getEndDate().getTime())){
				return false;
			}else if(this.alarmDate == null && ps.getAlarmDate() != null){
				return false;
			}else if(this.getAlarmDate() != null && ps.getAlarmDate() != null &&( this.getAlarmDate().getTime() != ps.getAlarmDate().getTime())){
				return false;
			}else if(!this.description.equals(ps.getDescription())){
				return false;
			}else if(this.priority != ps.getPriority()){
				return false;
			}else{
				return true;
			}
		}
		return super.equals(obj);
	}
	
	
	
}
