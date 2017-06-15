package com.raytrex.frontier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {

}
