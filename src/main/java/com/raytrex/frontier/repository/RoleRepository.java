package com.raytrex.frontier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
