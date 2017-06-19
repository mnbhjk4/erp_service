package com.raytrex.rpv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.rpv.repository.bean.OrderList;

public interface OrderListRepository extends JpaRepository<OrderList, Integer> {

	public OrderList findOneByProjectNumber(String projectNumber);
}
