package com.raytrex.frontier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.QuotationItem;

public interface QuotationItemRespository extends JpaRepository<QuotationItem, String>{

	public List<QuotationItem> findAllByQuotationNo(String quotationNo);
}
