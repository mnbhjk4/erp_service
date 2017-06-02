package com.raytrex.erp.contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raytrex.erp.repository.DepartmentRepository;
import com.raytrex.erp.service.bean.Department;

@RestController
public class DepartmentController {
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@RequestMapping("/department/findAll")
	public List<Department> findAll(){
		return departmentRepository.findAll();
	}
	
	@RequestMapping("/department/findOne")
	public Department findOne(String dep_id){
		return departmentRepository.findOne(dep_id);
	}
	
	@RequestMapping("/department/save")
	public Department save(Department department){
		return departmentRepository.save(department);
	}

}
