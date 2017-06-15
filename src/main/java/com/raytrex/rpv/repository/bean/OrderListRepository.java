package com.raytrex.rpv.repository.bean;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderListRepository extends JpaRepository<OrderList, Integer> {

	public OrderList findOneByProjectNumber(String projectNumber);
}
