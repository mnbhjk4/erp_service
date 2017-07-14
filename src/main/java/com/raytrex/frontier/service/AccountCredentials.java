package com.raytrex.frontier.service;

import java.util.ArrayList;
import java.util.List;

import com.raytrex.frontier.repository.bean.Permission;

public class AccountCredentials {
	private String username;
	private String password;
	private List<Permission> permissionList = new ArrayList<Permission>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
