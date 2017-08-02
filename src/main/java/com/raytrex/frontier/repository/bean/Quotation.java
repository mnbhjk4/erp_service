package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="quotation")
@IdClass(value=QuotationId.class)
public class Quotation implements Serializable{
	
	@Expose
	@Id
	@Column(name="task_no")
	private String taskNo;
	
	@Id
	@Column(name="quotation_no")
	@Expose
	private String quotationNo;
	
	@Column(name="quotation_date")
	@Expose
	private Timestamp quotationDate;
	
	@Column(name="expiration_date")
	@Expose
	private String expirationDate;
	
	@Column(name="payment_terms")
	@Expose
	private String paymentTerms;
	
	@Column(name="inco_terms")
	@Expose
	private String incoTerms;
	
	@Column(name="lead_time")
	@Expose
	private String leadTime;
	
	@Column(name="customer_infomation")
	@Expose
	private String customerInfomation;
	
	@Column(name="quotation_issue_by")
	@Expose
	private String quotationIssueBy;
	
	@Column(name="update_time")
	@Expose
	private Timestamp updateTime;
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
	public Timestamp getQuotationDate() {
		return quotationDate;
	}
	public void setQuotationDate(Timestamp quotationDate) {
		this.quotationDate = quotationDate;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public String getIncoTerms() {
		return incoTerms;
	}
	public void setIncoTerms(String incoTerms) {
		this.incoTerms = incoTerms;
	}
	public String getLeadTime() {
		return leadTime;
	}
	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}
	public String getCustomerInfomation() {
		return customerInfomation;
	}
	public void setCustomerInfomation(String customerInfomation) {
		this.customerInfomation = customerInfomation;
	}
	public String getQuotationIssueBy() {
		return quotationIssueBy;
	}
	public void setQuotationIssueBy(String quotationIssueBy) {
		this.quotationIssueBy = quotationIssueBy;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
