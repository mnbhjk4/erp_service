package com.raytrex.frontier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {
	public List<Customer> findAllByCountry(String country);
	public List<Customer> findAllByCountryAndRegion(String country,String region);
	public List<Customer> findAllByCountryAndRegionAndName(String country,String region,String name);
}
