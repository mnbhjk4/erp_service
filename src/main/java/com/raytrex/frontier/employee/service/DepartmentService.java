package com.raytrex.frontier.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raytrex.frontier.repository.DepartmentRepository;
import com.raytrex.frontier.repository.bean.Department;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;
	
	public Department saveDepartment(Department department){
		return departmentRepository.save(department);
	}
}
