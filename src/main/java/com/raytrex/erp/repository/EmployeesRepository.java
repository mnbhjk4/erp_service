package com.raytrex.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.erp.service.bean.Employees;

public interface EmployeesRepository extends JpaRepository<Employees, String> {

}
