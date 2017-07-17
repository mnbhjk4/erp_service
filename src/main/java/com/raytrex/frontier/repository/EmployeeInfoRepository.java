package com.raytrex.frontier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.EmployeeInfo;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, String> {

}
