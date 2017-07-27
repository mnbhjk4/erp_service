package com.raytrex.frontier.repository.bean;

import java.io.Serializable;

public class KeysightPriceHistoryId implements Serializable{
	private String priceVersion;
	private String productNo;
	private String productOption;
	
	public String getPriceVersion() {
		return priceVersion;
	}
	public void setPriceVersion(String priceVersion) {
		this.priceVersion = priceVersion;
	}
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
	
}
