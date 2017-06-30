package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="task_comment")
public class TaskComment implements Serializable{
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name="task_comment_uuid")
	private String taskCommentUuid;

	@Column(name="task_no",nullable=false)
	private String taskNo;
	
	private String comment;

	@Column(name="comment_date")
	private Timestamp commentDate;
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

	public Timestamp getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Timestamp commentDate) {
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
	
	@PrePersist
	public void onCreate(){
		if(this.taskCommentUuid == null || this.taskCommentUuid.isEmpty()){
			this.taskCommentUuid = UUID.randomUUID().toString();
		}
		this.commentDate = new Timestamp(System.currentTimeMillis());
		
	}
	
	@PreUpdate
	public void onUpdate(){
		if(this.taskCommentUuid == null || this.taskCommentUuid.isEmpty()){
			this.taskCommentUuid = UUID.randomUUID().toString();
		}
		this.commentDate = new Timestamp(System.currentTimeMillis());
	}
}
