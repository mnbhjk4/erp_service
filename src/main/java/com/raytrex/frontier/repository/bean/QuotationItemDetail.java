package com.raytrex.frontier.repository.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="quotation_item_detail")
public class QuotationItemDetail implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="item_detail_id")
	@Expose
	private Integer itemDetailId;
	@Column(name="product_no")
	@Expose
	private String productNo;
	@Column(name="product_option")
	@Expose
	private String productOption;
	@Column(name="description")
	@Expose
	private String description;
	@Column(name="qty")
	@Expose
	private Integer qty;
	@Column(name="price")
	@Expose
	private Double price;
	@Column(name="exchange_rate")
	@Expose
	private Double exchangeRate;
	@Column(name="markup_rate")
	@Expose
	private Double markupRate;
	@Column(name="list_price")
	@Expose
	private Double listPrice;
	@Column(name="note")
	@Expose
	private String note;
	@Column(name="update_time")
	@Expose
	private Timestamp updateTime;
	
	@ManyToOne
	@JoinColumn(name="item_id")
	private QuotationItem quotationItem;
	
	public Integer getItemDetailId() {
		return itemDetailId;
	}
	public void setItemDetailId(Integer itemDetailId) {
		this.itemDetailId = itemDetailId;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public Double getMarkupRate() {
		return markupRate;
	}
	public void setMarkupRate(Double markupRate) {
		this.markupRate = markupRate;
	}
	public Double getListPrice() {
		return listPrice;
	}
	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public QuotationItem getQuotationItem() {
		return quotationItem;
	}
	public void setQuotationItem(QuotationItem quotationItem) {
		this.quotationItem = quotationItem;
	}
	public String getProductOption() {
		return productOption;
	}
	public void setProductOption(String productOption) {
		this.productOption = productOption;
	}
	
	
}
