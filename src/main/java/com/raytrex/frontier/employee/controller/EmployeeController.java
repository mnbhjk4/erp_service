package com.raytrex.frontier.employee.controller;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.raytrex.frontier.employee.service.EmployeeService;
import com.raytrex.frontier.repository.bean.Employee;
import com.raytrex.microsoft.service.ADALService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ADALService adalService;
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/initEmployee")
	public String setToken(String token) {
		Gson gson = new Gson();
		return gson.toJson(employeeService.initEmployeeRepositoryFromAzure(token));
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getCompanyUsers")
	public String getCompanyUser(String access_token){
		List<Employee> employees = employeeService.getCompanyUsers();
		Gson gson = new Gson();
		JsonArray array = new JsonArray();
		Encoder encoder = Base64.getEncoder(); 
		for(Employee e : employees){
			JsonObject obj = (JsonObject) gson.toJsonTree(e);
			if(e.getEmployeesInfo() != null){
				if(e.getEmployeesInfo().getImage() != null){
					String base64Image  = encoder.encodeToString(e.getEmployeesInfo().getImage());
					JsonObject empInfoObj = (JsonObject) obj.get("employeesInfo");
					empInfoObj.remove("image");
					if(!base64Image.isEmpty()){
						empInfoObj.addProperty("image", "data:image/jpeg;base64,"+base64Image);
					}
				}
			}
			array.add(obj);
		}
		return gson.toJson(array);
	}
}
