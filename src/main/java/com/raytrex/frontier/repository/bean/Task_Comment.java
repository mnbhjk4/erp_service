package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Task_Comment implements Serializable{
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name="task_comment_uuid")
	private String taskCommentUuid;
	
	@Column(name="task_no")
	private String taskNo;
	
	private String comment;

	@Column(name="comment_date")
	private Date commentDate;
	private String uid;
	
	@Column(name="attach_uuid")
	private String attachUuid;

	public String getTaskCommentUuid() {
		return taskCommentUuid;
	}

	public void setTaskCommentUuid(String taskCommentUuid) {
		this.taskCommentUuid = taskCommentUuid;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAttachUuid() {
		return attachUuid;
	}

	public void setAttachUuid(String attachUuid) {
		this.attachUuid = attachUuid;
	}
	
	
}
