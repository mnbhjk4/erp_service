package com.raytrex.rpv.repository.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="project_customer_po")
public class ProjectCustomerPo implements Serializable {
	@Id
	@Column(name="customer_po")
	private String customerPo;
	
	@Column(name="customer_po_date")
	private Date customerPoDate;
	
	@Column(name="customer_payment_terms")
	private String customerPaymentTerms;
	
	@Column(name="customer_incoterm")
	private String customerIncoterm;
	
	@Column(name="customer_shipping_date")
	private Date customerShippingDate;
	
	public String getCustomerPo() {
		return customerPo;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public Date getCustomerPoDate() {
		return customerPoDate;
	}
	public void setCustomerPoDate(Date customerPoDate) {
		this.customerPoDate = customerPoDate;
	}
	public String getCustomerPaymentTerms() {
		return customerPaymentTerms;
	}
	public void setCustomerPaymentTerms(String customerPaymentTerms) {
		this.customerPaymentTerms = customerPaymentTerms;
	}
	public String getCustomerIncoterm() {
		return customerIncoterm;
	}
	public void setCustomerIncoterm(String customerIncoterm) {
		this.customerIncoterm = customerIncoterm;
	}
	public Date getCustomerShippingDate() {
		return customerShippingDate;
	}
	public void setCustomerShippingDate(Date customerShippingDate) {
		this.customerShippingDate = customerShippingDate;
	}
	
	
}
