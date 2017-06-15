package com.raytrex.erp.contorller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.raytrex.erp.service.login.microsoft.ADALService;
import com.raytrex.frontier.employee.service.EmployeeService;
import com.raytrex.frontier.repository.EmployeeRepository;
import com.raytrex.frontier.repository.bean.Employee;
import com.raytrex.frontier.repository.bean.EmployeeInfo;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
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
		Iterator<Employee> employeesIt = employeeRepository.findAll().iterator();
		List<Employee> employeesList = new ArrayList<Employee>();
		while(employeesIt.hasNext()){
			Employee employee = employeesIt.next();
			employeesList.add(employee);
		}
		Gson gson = new Gson();
		
		return gson.toJson(employeesList);
	}
}
