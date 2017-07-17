package com.raytrex.frontier.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.raytrex.frontier.repository.bean.EmployeeRoles;

public interface EmployeeRolesRepository extends JpaRepository<EmployeeRoles, String> {
	
//	@Query(value="INSERT INTO　employee_roles (uid,role_id,from_date) VALUES (?1,?2,?3)")
//	public void insert(String uid,String roleId,Date fromDate);
}
