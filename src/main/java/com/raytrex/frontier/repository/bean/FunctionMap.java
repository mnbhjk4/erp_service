package com.raytrex.frontier.repository.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="function_map")
public class FunctionMap {
	@Id
	@Column(name="function_name")
	@Expose
	private String functionName;
	
	@Column(name="parent_function_name")
	@Expose
	private String parentFunctionName;
	
	@Column(name="map_url")
	@Expose
	private String mapUrl;

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getParentFunctionName() {
		return parentFunctionName;
	}

	public void setParentFunctionName(String parentFunctionName) {
		this.parentFunctionName = parentFunctionName;
	}

	public String getMapUrl() {
		return mapUrl;
	}

	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
}
