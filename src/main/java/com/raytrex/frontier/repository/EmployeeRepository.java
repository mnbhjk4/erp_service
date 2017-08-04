package com.raytrex.frontier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.raytrex.frontier.repository.bean.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	public Employee findOneByEmpNo(String emp_no);
	
	@Query("SELECT e FROM Employee e join e.roleList er join er.role r WHERE r.depId = ?1")
	public List<Employee> getEmployeeWithDepId(String depId);
	
}
