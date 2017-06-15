package com.raytrex.rpv.repository.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="order_list")
public class OrderList implements Serializable {
	@Id
	@Column(name="order_list_number")
	private Integer orderListNumber;
	
	@Column(name="project_number")
	private String projectNumber;
	
	private String salesPerson;
	
	private String engineer;
	
	private String customer;
	
	private String contractors;
	
	private String location;
	
	private String types;
	
	private String model;
	
	private String config;
	
	@Column(name="raytrex_po_date")
	private Date raytrexPoDate;
	
	@Column(name="move_in")
	private Date moveIn;
	
	@Column(name="install_hw")
	private Date installHW;
	
	@Column(name="install_sw")
	private Date installSW;
	
	@Column(name="install_measure")
	private Date installMeasure;
	
	private Date uat;
	
	private Date fat;
	
	@Column(name="sales_amount")
	private Integer salesAmount;
	
	@Column(name="customer_po")
	private String customerPo;
	
	@Column(name="order_source")
	private String orderSource;
	
	@Column(name="sales_remarks")
	private String salesRemarks;
	
	@Column(name="engineer_remarks")
	private String engineerRemarks;
	
	@Column(name="order_delete")
	private Integer orderDelete;
	
	@Column(name="update_date")
	private Date updateDate;
	
	public Integer getOrderListNumber() {
		return orderListNumber;
	}
	public void setOrderListNumber(Integer orderListNumber) {
		this.orderListNumber = orderListNumber;
	}
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public String getSalesPerson() {
		return salesPerson;
	}
	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}
	public String getEngineer() {
		return engineer;
	}
	public void setEngineer(String engineer) {
		this.engineer = engineer;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getContractors() {
		return contractors;
	}
	public void setContractors(String contractors) {
		this.contractors = contractors;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public Date getRaytrexPoDate() {
		return raytrexPoDate;
	}
	public void setRaytrexPoDate(Date raytrexPoDate) {
		this.raytrexPoDate = raytrexPoDate;
	}
	public Date getMoveIn() {
		return moveIn;
	}
	public void setMoveIn(Date moveIn) {
		this.moveIn = moveIn;
	}
	public Date getInstallHW() {
		return installHW;
	}
	public void setInstallHW(Date installHW) {
		this.installHW = installHW;
	}
	public Date getInstallSW() {
		return installSW;
	}
	public void setInstallSW(Date installSW) {
		this.installSW = installSW;
	}
	public Date getInstallMeasure() {
		return installMeasure;
	}
	public void setInstallMeasure(Date installMeasure) {
		this.installMeasure = installMeasure;
	}
	
	public Date getUat() {
		return uat;
	}
	public void setUat(Date uat) {
		this.uat = uat;
	}
	public Date getFat() {
		return fat;
	}
	public void setFat(Date fat) {
		this.fat = fat;
	}
	public Integer getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(Integer salesAmount) {
		this.salesAmount = salesAmount;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getSalesRemarks() {
		return salesRemarks;
	}
	public void setSalesRemarks(String salesRemarks) {
		this.salesRemarks = salesRemarks;
	}
	public String getEngineerRemarks() {
		return engineerRemarks;
	}
	public void setEngineerRemarks(String engineerRemarks) {
		this.engineerRemarks = engineerRemarks;
	}
	public Integer getOrderDelete() {
		return orderDelete;
	}
	public void setOrderDelete(Integer orderDelete) {
		this.orderDelete = orderDelete;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
}
