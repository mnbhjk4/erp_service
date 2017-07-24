package com.raytrex.frontier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	public Employee findOneByEmpNo(String emp_no);
	
}
