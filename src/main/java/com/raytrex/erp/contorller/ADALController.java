package com.raytrex.erp.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raytrex.erp.service.login.microsoft.ADALService;

@RestController
public class ADALController {
	@Autowired
	private ADALService adalService;
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/adal/getToken")
	public String getToken( String code,String scope,String redirectUri){
		return adalService.getToken(code, scope, redirectUri);
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/adal/getTokenByRefreshToken")
	public String getTokenByRefreshToken( String refresh_token,String scope,String redirectUri){
		return adalService.getTokenByRefreshToken(refresh_token, scope, redirectUri);
	}
}
