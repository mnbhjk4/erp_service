package com.raytrex.frontier.employee.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.raytrex.frontier.employee.service.EmployeeService;
import com.raytrex.frontier.repository.bean.Employee;
import com.raytrex.frontier.repository.bean.EmployeeInfo;
import com.raytrex.frontier.repository.bean.EmployeeRoles;
import com.raytrex.frontier.repository.bean.FunctionMap;
import com.raytrex.frontier.repository.bean.Permission;
import com.raytrex.frontier.utils.GsonUtil;
import com.raytrex.microsoft.service.ADALService;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = { "*", "http://localhost:8100" })
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ADALService adalService;

	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping("/initEmployee")
	public String setToken(String token) {
		Gson gson = new Gson();
		return gson.toJson(employeeService.initEmployeeRepositoryFromAzure(token));
	}

	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping("/getCompanyUsers")
	public String getCompanyUser() {
		List<Employee> employees = employeeService.getCompanyUsers();
		Gson gson = GsonUtil.getGson();
		return gson.toJson(employees);
	}

	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping("/addEmployee")
	public String addEmployee(String employee, String permissionList, String access_token) {
		Gson gson = GsonUtil.getGson();
		Employee emp = gson.fromJson(employee, Employee.class);
		// 先去找找看是否已經建立好Office365
		try {
			String user365 = adalService.listUsers(access_token);
			Map officeResult = gson.fromJson(user365, Map.class);
			if (officeResult.containsKey("value")) {
				Object users = officeResult.get("value");
				if (users instanceof List) {
					List userList = (List) users;
					for (Object m : userList) {
						if (m instanceof Map) {
							Object mail = ((Map) m).get("mail");
							if (mail != null && mail.toString().equals(emp.getMail())) {
								Object id = ((Map) m).get("id");
								if (id != null) {
									emp.setUid(id.toString());
									break;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {

		}
		if (emp.getUid() == null || emp.getUid().isEmpty()) {
			emp.setUid(UUID.randomUUID().toString());
		}

		EmployeeInfo employeeInfo = emp.getEmployeesInfo();// 對EmployeeInfo建立連結
		List<EmployeeRoles> roleList = emp.getRoleList();
		emp.setEmployeesInfo(null);
		emp.setRoleList(new ArrayList<EmployeeRoles>());
		// 儲存Employee
		employeeService.saveEmployee(emp);
		// 儲存EmployeeInfo
		employeeInfo.setUid(emp.getUid());
		employeeService.saveEmployeeInfo(employeeInfo);
		// 儲存EmployeeRoles
		for (EmployeeRoles r : roleList) {
			r.setEmployee(emp);
			if(r.getFromDate() == null){
				r.setFromDate(new Date());
			}
			employeeService.saveEmployeeRoles(r);
		}
		
		Type type = new TypeToken<List<Permission>>() {}.getType();
		List<Permission> pList = gson.fromJson(permissionList, type);
		for (Permission p : pList) {
			p.setUid(emp.getUid());
			p.setPermissionId(UUID.randomUUID().toString());
			p.setRoleId(null);
			employeeService.savePermission(p);
		}
		return gson.toJson(employeeService.getCompanyUsers());
	}
	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping("/saveEmployee")
	public String saveEmployee(String employee, String permissionList, String access_token) {
		Gson gson = GsonUtil.getGson();
		Employee emp = gson.fromJson(employee, Employee.class);
		// 先去找找看是否已經建立好Office365(後續建好時要求再跑一次,否則將無法SYNC)
		try {
			String user365 = adalService.listUsers(access_token);
			Map officeResult = gson.fromJson(user365, Map.class);
			if (officeResult.containsKey("value")) {
				Object users = officeResult.get("value");
				if (users instanceof List) {
					List userList = (List) users;
					for (Object m : userList) {
						if (m instanceof Map) {
							Object mail = ((Map) m).get("mail");
							if (mail != null && mail.toString().equals(emp.getMail())) {
								Object id = ((Map) m).get("id");
								if (id != null) {
									emp.setUid(id.toString());
									break;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {

		}
		if (emp.getUid() == null || emp.getUid().isEmpty()) {
			emp.setUid(UUID.randomUUID().toString());
		}

		
		// 儲存EmployeeRoles
		List<EmployeeRoles> roleList = emp.getRoleList();
		List<EmployeeRoles> dbRoleList = employeeService.getEmployee(emp.getUid()).getRoleList();
		for (EmployeeRoles r : roleList) {
			Iterator<EmployeeRoles> dbIt = dbRoleList.iterator();
			while(dbIt.hasNext()){
				EmployeeRoles dbRole = dbIt.next();
				if(dbRole.getToDate() != null){
					dbIt.remove();
				}else if(dbRole.getRole().getRoleId().equals(r.getRole().getRoleId())){
					r.setFromDate(dbRole.getFromDate());
					r.seteIndex(dbRole.geteIndex());
					dbIt.remove();
				}
			}
			r.setEmployee(emp);
			if(r.getFromDate() == null){
				r.setFromDate(new Date());
				employeeService.saveEmployeeRoles(r);
			}
			
		}
		//離開Role的部分
		for(EmployeeRoles r: dbRoleList){
			r.setToDate(new Date());
			employeeService.saveEmployeeRoles(r);
		}
		
		Type type = new TypeToken<List<Permission>>() {}.getType();
		List<Permission> pList = gson.fromJson(permissionList, type);
		List<Permission> dbPList = employeeService.getPermissionByUid(emp.getUid());
		for (Permission p : pList) {
			p.setUid(emp.getUid());
			Permission targetP = null;
			for(Permission dbP : dbPList){
				if(dbP.getUid().equals(p.getUid()) && dbP.getFunctionName().equals(p.getFunctionName())){
					targetP = dbP;
					break;
				}
			}
			if(targetP != null){
				p.setPermissionId(targetP.getPermissionId());
				p.setPermissionSerial(targetP.getPermissionSerial());
			}else{
				p.setPermissionId(UUID.randomUUID().toString());
			}
			p.setRoleId(null);
			employeeService.savePermission(p);
		}
		
		
	
		// 儲存EmployeeInfo
		EmployeeInfo employeeInfo = emp.getEmployeesInfo();// 對EmployeeInfo建立連結
		employeeInfo.setUid(emp.getUid());
		employeeService.saveEmployeeInfo(employeeInfo);
		// 儲存Employee
		emp.setEmployeesInfo(null);
		emp.setRoleList(new ArrayList<EmployeeRoles>());
		employeeService.saveEmployee(emp);
		
		return gson.toJson(employeeService.getCompanyUsers());
	}
	
	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping("/getEmployee")
	public String getEmployee(String uid) {
		Gson gson = GsonUtil.getGson();
		Employee employee = employeeService.getEmployee(uid);
		JsonObject jo = new JsonObject();
		if(employee != null){
			List<Permission> permissionList = employeeService.getPermissionByUid(uid);
			jo.add("permission", gson.toJsonTree(permissionList));
		}
		if(employee.getRoleList() != null){
			Iterator<EmployeeRoles> roleIt = employee.getRoleList().iterator();
			while(roleIt.hasNext()){
				EmployeeRoles r = roleIt.next();
				if(r.getToDate() != null){
					roleIt.remove();
				}
			}
		}
		jo.add("employee", gson.toJsonTree(employee));
		return gson.toJson(jo);
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getRTXNo")
	public String getRTXNo(){
		String rtxno = employeeService.getNewEmployeeNo();
		Gson gson = GsonUtil.getGson();
		return gson.toJson(rtxno);
	}
}
