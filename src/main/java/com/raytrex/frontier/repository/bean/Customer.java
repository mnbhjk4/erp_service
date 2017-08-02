package com.raytrex.frontier.repository.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="customer")
public class Customer {
	@Id
	@Column(name="customer_id")
	@Expose
	private String customerId;
	
	@Expose
	private String name;
	
	@Expose
	private String subname;
	
	@Expose
	private String country;
	
	@Expose
	private String region;
	
	@Column(name="short_name")
	@Expose
	private String shrotName;
	
	@Expose
	private String cname;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubname() {
		return subname;
	}

	public void setSubname(String subname) {
		this.subname = subname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getShrotName() {
		return shrotName;
	}

	public void setShrotName(String shrotName) {
		this.shrotName = shrotName;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
}
