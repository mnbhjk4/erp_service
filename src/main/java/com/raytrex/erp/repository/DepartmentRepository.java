package com.raytrex.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.erp.service.bean.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {

}
