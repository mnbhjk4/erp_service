package com.raytrex.frontier.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raytrex.frontier.repository.FunctionMapRepository;
import com.raytrex.frontier.repository.RoleRepository;
import com.raytrex.frontier.repository.bean.FunctionMap;
import com.raytrex.frontier.repository.bean.Role;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private FunctionMapRepository functionMapRepository;
	public List<Role> getAllRoles(){
		return roleRepository.findAllByOrderByRoleLevel();
	}
	
	public List<FunctionMap> getAllFunctionMap(){
		return functionMapRepository.findAll();
	}
	
	public List<Role> getRoleByDepId(String depId){
		return roleRepository.findAllByDepId(depId);
	}

}
