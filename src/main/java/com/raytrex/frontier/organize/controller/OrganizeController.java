package com.raytrex.frontier.organize.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.raytrex.frontier.repository.DepartmentRepository;
import com.raytrex.frontier.repository.bean.Department;

@RestController
@RequestMapping("/organize")
public class OrganizeController {
	@Autowired
	private DepartmentRepository departmentRepository;
	
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
}
