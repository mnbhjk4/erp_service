package com.raytrex.frontier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.Quotation;

public interface QuotationRespository extends JpaRepository<Quotation, String>{
	public List<Quotation> findAllByTaskNo(String taskNo);
}
