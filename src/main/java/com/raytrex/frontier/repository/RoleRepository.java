package com.raytrex.frontier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
	
	public List<Role> findAllByOrderByRoleLevel();
}
