package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="keysight_price_history")
@IdClass(value=KeysightPriceHistoryId.class)
public class KeysightPriceHistory implements Serializable{
	@Id
	@Column(name="price_version")
	private String priceVersion;
	
	@Id
	@Column(name="product_no")
	@Expose
	private String productNo;
	
	@Id
	@Column(name="product_option")
	@Expose
	private String productOption;
	
	@Column(name="description")
	@Expose
	private String description;
	
	@Column(name="list_price")
	@Expose
	private String listPrice;
	
	@Column(name="list_currency")
	@Expose
	private String listCurrency;
	
	@Column(name="discount_qualifier_group")
	@Expose
	private String discountQulifierGroup;
	
	@Column(name="discount_percentage")
	@Expose
	private String discountPersentage;
	
	@Column(name="net_price")
	@Expose
	private String netPrice;
	
	@Column(name="net_currency")
	@Expose
	private String netCurrency;
	
	@Column(name="product_line")
	@Expose
	private String productLine;
	
	@Column(name="rmu_code")
	@Expose
	private String rmuCode;
	
	@Column(name="last_order_date")
	@Expose
	private String lastOrderDate;
	
	@Column(name="update_time")
	private Timestamp updateTime;
	
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	public String getProductOption() {
		return productOption;
	}
	public void setProductOption(String productOption) {
		this.productOption = productOption;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getListPrice() {
		return listPrice;
	}
	public void setListPrice(String listPrice) {
		this.listPrice = listPrice;
	}
	
	public String getListCurrency() {
		return listCurrency;
	}
	public void setListCurrency(String listCurrency) {
		this.listCurrency = listCurrency;
	}
	public String getDiscountQulifierGroup() {
		return discountQulifierGroup;
	}
	public void setDiscountQulifierGroup(String discountQulifierGroup) {
		this.discountQulifierGroup = discountQulifierGroup;
	}
	public String getDiscountPersentage() {
		return discountPersentage;
	}
	public void setDiscountPersentage(String discountPersentage) {
		this.discountPersentage = discountPersentage;
	}
	public String getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(String netPrice) {
		this.netPrice = netPrice;
	}
	public String getNetCurrency() {
		return netCurrency;
	}
	public void setNetCurrency(String netCurrency) {
		this.netCurrency = netCurrency;
	}
	public String getProductLine() {
		return productLine;
	}
	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}
	public String getRmuCode() {
		return rmuCode;
	}
	public void setRmuCode(String rmuCode) {
		this.rmuCode = rmuCode;
	}
	public String getLastOrderDate() {
		return lastOrderDate;
	}
	public void setLastOrderDate(String lastOrderDate) {
		this.lastOrderDate = lastOrderDate;
	}
	public String getPriceVersion() {
		return priceVersion;
	}
	public void setPriceVersion(String priceVersion) {
		this.priceVersion = priceVersion;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
