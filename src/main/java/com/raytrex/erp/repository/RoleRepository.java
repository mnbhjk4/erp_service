package com.raytrex.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.erp.service.bean.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}