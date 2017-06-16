package com.raytrex.frontier.repository.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="project_owner")
public class ProjectOwner {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="owner_serial")
	private Integer ownerSerial;
	
	@Column(name="project_no")
	private String projectNo;
	
	private String uid;
	
	@Column(name="join_date")
	private Date joinDate;
	
	@Column(name="leave_date")
	private Date leaveDate;

	public Integer getOwnerSerial() {
		return ownerSerial;
	}

	public void setOwnerSerial(Integer ownerSerial) {
		this.ownerSerial = ownerSerial;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
}
