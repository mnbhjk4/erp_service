package com.raytrex.frontier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
