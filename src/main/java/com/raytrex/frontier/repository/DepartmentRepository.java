package com.raytrex.frontier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.raytrex.frontier.repository.bean.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {

	@Query("select d from Department d where parentDepId = ?1")
	public List<Department> getChildDepartment(String depIdt);
}
