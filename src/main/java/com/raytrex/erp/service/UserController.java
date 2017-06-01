package com.raytrex.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.raytrex.erp.service.login.microsoft.ADALService;

@RestController
public class UserController {
	@Autowired
	private ADALService adalService;
	/* Maps to all HTTP actions by default (GET,POST,..) */
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/accessToken")
	public @ResponseBody String getUsers(String token,String scope) {
		String result = adalService.getToken(token,scope, "http://localhost:8100");
		return result;
	}
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/token")
	public @ResponseBody String setToken(String token) {
		return "{\"users\":[{\"firstname\":\"Richard\", \"lastname\":\"Feynman\"},"
				+ "{\"firstname\":\"Marie\",\"lastname\":\"Curietest\"}]}";
	}
}
