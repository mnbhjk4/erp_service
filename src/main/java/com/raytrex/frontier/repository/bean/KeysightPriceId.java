package com.raytrex.frontier.repository.bean;

import java.io.Serializable;

public class KeysightPriceId implements Serializable{
	private String productNo;
	private String productOption;
	
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
