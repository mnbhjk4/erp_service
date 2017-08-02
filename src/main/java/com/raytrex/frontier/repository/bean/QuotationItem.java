package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="quotation_item")
public class QuotationItem implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="item_id")
	@Expose
	private Integer itemId;
	@Column(name="item_index")
	@Expose
	private Integer itemIndex;
	@Column(name="quotation_no")
	@Expose
	private String quotationNo;
	@Column(name="description")
	@Expose
	private String description;
	@Column(name="unit_price")
	@Expose
	private String unitPrice;
	@Column(name="qtr")
	@Expose
	private String qtr;
	@Column(name="extened_price")
	@Expose
	private String extenedPrice;
	@Column(name="currency")
	@Expose
	private String currency;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="quotationItem")
	@Expose
	private List<QuotationItemDetail> quotationItemDetailList;
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getItemIndex() {
		return itemIndex;
	}
	public void setItemIndex(Integer itemIndex) {
		this.itemIndex = itemIndex;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public String getExtenedPrice() {
		return extenedPrice;
	}
	public void setExtenedPrice(String extenedPrice) {
		this.extenedPrice = extenedPrice;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getQtr() {
		return qtr;
	}
	public void setQtr(String qtr) {
		this.qtr = qtr;
	}
	public String getQuotationNo() {
		return quotationNo;
	}
	public void setQuotationNo(String quotationNo) {
		this.quotationNo = quotationNo;
	}
	public List<QuotationItemDetail> getQuotationItemDetailList() {
		return quotationItemDetailList;
	}
	public void setQuotationItemDetailList(List<QuotationItemDetail> quotationItemDetailList) {
		this.quotationItemDetailList = quotationItemDetailList;
	}
	
	
}
