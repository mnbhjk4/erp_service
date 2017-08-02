package com.raytrex.frontier.repository.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="department")
public class Department implements Serializable{
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name="dep_id")
	@Expose
	private String depId;
	@Column(name="dep_no")
	@Expose
	private String depNo;
	@Expose
	private String name;
	@Expose
	private String region;
	@Column(name="parent_dep_id")
	@Expose
	private String parentDepId;
	
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public String getDepNo() {
		return depNo;
	}
	public void setDepNo(String depNo) {
		this.depNo = depNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getParentDepId() {
		return parentDepId;
	}
	public void setParentDepId(String parentDepId) {
		this.parentDepId = parentDepId;
	}
}
