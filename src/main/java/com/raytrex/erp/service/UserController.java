package com.raytrex.erp.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.raytrex.erp.repository.EmployeesRepository;
import com.raytrex.erp.service.bean.Employees;
import com.raytrex.erp.service.login.microsoft.ADALService;

@RestController
public class UserController {
	@Autowired
	private EmployeesRepository employeesRepository;
	@Autowired
	private ADALService adalService;
	/* Maps to all HTTP actions by default (GET,POST,..) */
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/accessToken")
	public @ResponseBody String getUsers(String token,String scope) {
		String result = adalService.getToken(token,scope, "http://localhost:8100");
		return result;
	}
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/token")
	public @ResponseBody String setToken(String token) {
		Iterator<Employees> employeesIt = employeesRepository.findAll().iterator();
		List<Employees> employeesList = new ArrayList<Employees>();
		while(employeesIt.hasNext()){
			Employees employee = employeesIt.next();
			employeesList.add(employee);
		}
		Gson gson = new Gson();
		
		return gson.toJson(employeesList);
	}
}
