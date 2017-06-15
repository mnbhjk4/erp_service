package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Task_Status implements Serializable {
	@Id
	@Column(name="task_status_id")
	private Integer taskStatusId;
	
	@Column(name="task_no")
	private String task_no;
	
	@Column(name="update_time")
	private Date updateTime;
	
	private String status;
	
	private Integer priority;
	
	@Column(name="startDate")
	private Date start_date;
	
	@Column(name="dueDate")
	private Date due_date;
	
	@Column(name="alertDate")
	private Date alert_date;
	
	@Column(name="endDate")
	private Date end_date;
	
	@Column(name="taskIndex")
	private Integer task_index;
	
	@Column(name="parentTaskNo")
	private String parent_task_no;

	public Integer getTaskStatusId() {
		return taskStatusId;
	}

	public void setTaskStatusId(Integer taskStatusId) {
		this.taskStatusId = taskStatusId;
	}

	public String getTask_no() {
		return task_no;
	}

	public void setTask_no(String task_no) {
		this.task_no = task_no;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
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

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public Date getAlert_date() {
		return alert_date;
	}

	public void setAlert_date(Date alert_date) {
		this.alert_date = alert_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Integer getTask_index() {
		return task_index;
	}

	public void setTask_index(Integer task_index) {
		this.task_index = task_index;
	}

	public String getParent_task_no() {
		return parent_task_no;
	}

	public void setParent_task_no(String parent_task_no) {
		this.parent_task_no = parent_task_no;
	}
}
