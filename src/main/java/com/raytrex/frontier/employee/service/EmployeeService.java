package com.raytrex.frontier.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.raytrex.erp.service.login.microsoft.ADALService;
import com.raytrex.frontier.repository.EmployeeRepository;
import com.raytrex.frontier.repository.bean.Employee;
import com.raytrex.frontier.repository.bean.EmployeeInfo;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ADALService adalService;

	public void initEmployeeRepositoryFromAzure(String access_token){
		String userListString = adalService.listUsers(access_token);
		Gson gson = new Gson();
		Map result = gson.fromJson(userListString, Map.class);
		if(result.containsKey("value")){
			Object valueObject= result.get("value");
			if(valueObject instanceof List){
				List<Map> valueList = (List<Map>) valueObject;
				List<Employee> employeesList = new ArrayList<Employee>();
				for(Map value : valueList){
					Employee employees = new Employee();
					employees.setUid(value.get("id").toString());
					employees.setMail(value.get("mail") == null?"":value.get("mail").toString());
					employees.setEmpNo(employees.getUid());
					EmployeeInfo info = new EmployeeInfo();
					info.setUid(employees.getUid());
					info.setLastName(value.get("displayName") == null?"":value.get("displayName").toString());
					info.setFirstName(value.get("givenName") == null?"":value.get("givenName").toString());
					info.setPreferredLanguage(value.get("preferredLanguage") == null?"zh_TW": value.get("preferredLanguage").toString());
					employees.setEmployeesInfo(info);
					employeesList.add(employees);
				}
				employeeRepository.save(employeesList);
			}
		}
	}
}
