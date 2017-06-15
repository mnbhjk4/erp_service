package com.raytrex.frontier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {

}
