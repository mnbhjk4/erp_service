package com.raytrex.frontier.repository.bean;

import java.io.Serializable;

import javax.persistence.Id;


public class QuotationId implements Serializable{

	private String taskNo;

	private String quotationNo;
	
	
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getQuotationNo() {
		return quotationNo;
	}
	public void setQuotationNo(String quotationNo) {
		this.quotationNo = quotationNo;
	}
}
