package com.raytrex.erp.service;

import org.springframework.data.repository.CrudRepository;

import com.raytrex.erp.service.bean.Employees;

public interface EmployeesRepository extends CrudRepository<Employees, String> {

}
