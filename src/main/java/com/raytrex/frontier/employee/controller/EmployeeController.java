package com.raytrex.frontier.employee.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.raytrex.frontier.employee.service.EmployeeService;
import com.raytrex.frontier.repository.bean.Employee;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/token")
	public @ResponseBody String setToken(String token) {
		employeeService.initEmployeeRepositoryFromAzure(token);

		
		return "[]";
	}
}
