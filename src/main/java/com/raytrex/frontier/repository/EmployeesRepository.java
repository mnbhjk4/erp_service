package com.raytrex.frontier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.Employees;

public interface EmployeesRepository extends JpaRepository<Employees, String> {

}
