package com.raytrex.frontier.organize.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import com.raytrex.frontier.employee.service.RoleService;
import com.raytrex.frontier.repository.DepartmentRepository;
import com.raytrex.frontier.repository.PermissionRepository;
import com.raytrex.frontier.repository.RoleRepository;
import com.raytrex.frontier.repository.bean.Department;
import com.raytrex.frontier.repository.bean.FunctionMap;
import com.raytrex.frontier.repository.bean.Permission;
import com.raytrex.frontier.repository.bean.Role;
import com.raytrex.frontier.utils.GsonUtil;

@RestController
@RequestMapping("/organize")
public class OrganizeController {
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private EmployeeService employeeService;

	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getDepartmentsTree")
	public String getDepartmentsTree(){
		List<Department> departmentList = this.departmentRepository.findAll();
		List<List<Department>> departmentTree = new ArrayList<List<Department>>();
		//先放第一層
		Iterator<Department> departIt = departmentList.iterator();
		List<Department> rootDepartmentList = new ArrayList<Department>();
		while(departIt.hasNext()){
			Department department = departIt.next();
			if(department.getParentDepId() == null || department.getParentDepId().isEmpty()){
				rootDepartmentList.add(department);
				departIt.remove();
			}
		}
		departmentTree.add(rootDepartmentList);
		//無限迴圈到把剩下所有的Department給跑完
		while(!departmentList.isEmpty()){
			loopDepartmentTree(departmentTree, departmentList);
		}
		Gson gson = new Gson();
		return gson.toJson(departmentTree);
	}
	
	private void loopDepartmentTree(List<List<Department>> departmentTree,List<Department> departmentList){
		List<Department> parentDepartmentList = departmentTree.get(departmentTree.size()-1);
		Iterator<Department> departIt = departmentList.iterator();
		List<Department> childrenDepartmentList = new ArrayList<Department>();
		while(departIt.hasNext()){
			Department childDepartment = departIt.next();
			for(Department parentDepartment : parentDepartmentList){
				if(childDepartment.getParentDepId() != null && childDepartment.getParentDepId().equals(parentDepartment.getDepId())){
					childrenDepartmentList.add(childDepartment);
					departIt.remove();
				}
			}
		}
		if(!childrenDepartmentList.isEmpty()){
			departmentTree.add(childrenDepartmentList);
		}
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getAllRoles")
	public String getAllRoles(){
		List<Role> roles = roleService.getAllRoles();
		Gson gson = GsonUtil.getGson();
		
		return gson.toJson(roles);
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getAllFunctionMap")
	public String getAllFunctionMap(){
		List<FunctionMap> roles = roleService.getAllFunctionMap();
		Gson gson = GsonUtil.getGson();
		return gson.toJson(roles);
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getDepartmentList")
	public String getDepartmentList(){
		List<Department> departmentList = this.departmentRepository.findAll();
		Gson gson = GsonUtil.getGson();
		JsonArray array = new JsonArray();
		for(Department d : departmentList){
			JsonObject o = (JsonObject) gson.toJsonTree(d);
			List<Role> roleList = this.roleService.getRoleByDepId(d.getDepId());
			o.add("roleList", gson.toJsonTree(roleList));
			array.add(o);
		}
		return gson.toJson(array);
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getPermissionByRole")
	public String getPermissionByRole(String roleId){
		List<Permission> permissionList = this.employeeService.getPermissionByRoleId(roleId);
		Gson gson = GsonUtil.getGson();
		return gson.toJson(permissionList);
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/saveDepartment")
	public String addDepartment(String department){
		Gson gson = GsonUtil.getGson();
		Department d = gson.fromJson(department, Department.class);
		Department newd = departmentRepository.save(d);
		return gson.toJson(newd);
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/saveRole")
	public String saveRole(String role,String permissionList){
		Gson gson = GsonUtil.getGson();
		Role r = gson.fromJson(role, Role.class);
		Type type = new TypeToken<List<Permission>>(){}.getType();
		if(r.getRoleId() == null || r.getRoleId().isEmpty()){
			r.setRoleId(UUID.randomUUID().toString());
		}
		if(r.getDepartment() == null && r.getDepId() != null && !r.getDepId().isEmpty()){
			r.setDepartment(departmentRepository.findOne(r.getDepId()));
		}
		Role newRole = roleRepository.save(r);
		List<Permission> pList = gson.fromJson(permissionList, type);
		for(Permission p : pList){
			if(p.getRoleId() == null){
				p.setRoleId(newRole.getRoleId());
			}
			
			if(p.getPermissionId() == null){
				p.setPermissionId(UUID.randomUUID().toString());
			}
			permissionRepository.save(p);
		}
		
		return gson.toJson("");
	}
}
