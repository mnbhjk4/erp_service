package com.raytrex.microsoft.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.raytrex.microsoft.service.ADALService;

@RestController
@RequestMapping("/adal")
public class ADALController {
	@Autowired
	private ADALService adalService;
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getToken")
	public String getToken( String code,String scope,String redirectUri){
		return adalService.getToken(code, scope, redirectUri);
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getTokenByRefreshToken")
	public String getTokenByRefreshToken( String refresh_token,String scope,String redirectUri){
		return adalService.getTokenByRefreshToken(refresh_token, scope, redirectUri);
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getCompanyUserMap")
	public String getCompanyUserMap( String access_token){
		return adalService.listUsers(access_token);
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getUserProfilePhoto")
	public String getUserProfilePhoto(String access_token){
		return adalService.getUserProfilePhoto(access_token);
	}
	
	
}
