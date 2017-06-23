package com.raytrex.frontier.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.raytrex.frontier.repository.DepartmentRepository;
import com.raytrex.frontier.repository.EmployeeRepository;
import com.raytrex.frontier.repository.PermissionRepository;
import com.raytrex.frontier.repository.RoleRepository;
import com.raytrex.frontier.repository.bean.Employee;
import com.raytrex.frontier.repository.bean.EmployeeInfo;
import com.raytrex.microsoft.service.ADALService;

@Service
public class EmployeeService {
	@Autowired
	private ADALService adalService;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	
	/**
	 * 初始化Azure上所有的帳號,到Frontier的身上
	 * @param access_token
	 */
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
					byte[] image = adalService.getUserProfilePhoto(access_token, employees.getUid(), info.getLastName());
					if(image != null){
						info.setImage(image);
					}
					employees.setEmployeesInfo(info);
					employeesList.add(employees);
				}
				employeeRepository.save(employeesList);
			}
		}
	}
	
	public List<Employee> getCompanyUsers(){
		return employeeRepository.findAll();
	}
	
	public Employee saveEmployee(Employee employee){
		return employeeRepository.save(employee);
	}
}
