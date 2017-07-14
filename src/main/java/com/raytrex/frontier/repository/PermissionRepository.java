package com.raytrex.frontier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {

	public List<Permission> findByRoleId(String roleId);
	public List<Permission> findByUid(String uid);
}
