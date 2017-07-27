package com.raytrex.frontier.project.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.raytrex.frontier.repository.CustomerRepository;
import com.raytrex.frontier.repository.bean.Customer;
import com.raytrex.frontier.utils.GsonUtil;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;
	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping(value="/getAllCusomterCountry",method=RequestMethod.POST)
	public String getAllCusomterCountry(){
		Gson gson = GsonUtil.getGson();
		List<Customer> l = customerRepository.findAll();
		HashSet<String> customerSet = new HashSet<String>(); 
		for(Customer customer : l){
			if(!customerSet.contains(customer.getCountry())){
				customerSet.add(customer.getCountry());
			}
		}
		return gson.toJson(customerSet);
	}
	
	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping(value="/getAllCusomterRegion",method=RequestMethod.POST)
	public String getAllCusomterRegion(String country){
		Gson gson = GsonUtil.getGson();
		
		List<Customer> l = customerRepository.findAllByCountry(country);
		HashSet<String> customerSet = new HashSet<String>(); 
		for(Customer customer : l){
			if(!customerSet.contains(customer.getRegion())){
				customerSet.add(customer.getRegion());
			}
		}
		return gson.toJson(customerSet);
	}
	
	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping(value="/getAllCusomterCompany",method=RequestMethod.POST)
	public String getAllCusomterCompany(String country,String region){
		Gson gson = GsonUtil.getGson();
		List<Customer> l = customerRepository.findAllByCountryAndRegion(country, region);
		HashSet<String> customerSet = new HashSet<String>(); 
		for(Customer customer : l){
			if(!customerSet.contains(customer.getRegion())){
				customerSet.add(customer.getName());
			}
		}
		return gson.toJson(customerSet);
	}
	
	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping(value="/getAllCusomterTarget",method=RequestMethod.POST)
	public String getAllCusomterTarget(String country,String region,String name){
		Gson gson = GsonUtil.getGson();
		return gson.toJson(customerRepository.findAllByCountryAndRegionAndName(country, region, name));
	}
}

