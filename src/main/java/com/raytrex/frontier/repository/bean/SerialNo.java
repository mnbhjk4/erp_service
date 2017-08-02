package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="serial_no")
public class SerialNo implements Serializable {
	@Id
	@Column(name="serial_name")
	@Expose
	private String serialName;
	
	@Expose
	private String description;
	
	@Expose
	private Integer count;
	
	@Column(name="update_time")
	@Expose
	private Date updateTime;
	public String getSerialName() {
		return serialName;
	}
	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@PrePersist
	public void onCreate(){
		if(this.updateTime == null){
			this.updateTime = new Date();
		}
	}
	
	@PreUpdate
	public void onUpdate(){
		if(this.updateTime == null){
			this.updateTime = new Date();
		}
	}
}
