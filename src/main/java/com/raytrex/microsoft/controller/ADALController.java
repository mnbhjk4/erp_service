package com.raytrex.microsoft.controller;

import java.util.Base64;
import java.util.Map;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.raytrex.frontier.repository.EmployeeRepository;
import com.raytrex.frontier.repository.bean.Employee;
import com.raytrex.frontier.utils.GsonUtil;
import com.raytrex.microsoft.service.ADALService;

@RestController
@RequestMapping("/adal")
public class ADALController {
	@Autowired
	private ADALService adalService;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getToken")
	public String getToken( @RequestBody String body){
		Gson gson = GsonUtil.getGson();
		Map parameterMap = gson.fromJson(body, Map.class);
		String token = adalService.getToken(parameterMap.get("code").toString(), parameterMap.get("scope").toString(), parameterMap.get("redirectUri").toString());
		if(!token.equals("[]")){
			Map tokenMap = gson.fromJson(token, Map.class);
			Object accessToken = tokenMap.get("access_token");
			if(accessToken != null && !accessToken.toString().isEmpty()){
				DecodedJWT decode = JWT.decode(accessToken.toString());
				Map<String, Claim> data = decode.getClaims();
				if(data != null &&  data.containsKey("oid") && data.containsKey("app_displayname")){
					String oid = data.get("oid").asString();
					String name = data.get("app_displayname").asString();
					byte[] image = adalService.getUserProfilePhoto(accessToken.toString(), oid, name);
					Employee employee = employeeRepository.findOne(oid.toString());
					if(employee != null){
						employee.getEmployeesInfo().setImage(image);
						employeeRepository.save(employee);
					}
				}
			}
			
		}
		return token;
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getTokenByRefreshToken")
	public String getTokenByRefreshToken( String refresh_token,String scope,String redirectUri){
		String token = adalService.getTokenByRefreshToken(refresh_token, scope, redirectUri);
		if(!token.equals("[]")){
			Gson gson = new Gson();
			Map tokenMap = gson.fromJson(token, Map.class);
			Object accessToken = tokenMap.get("access_token");
			if(accessToken != null && !accessToken.toString().isEmpty()){
				DecodedJWT decode = JWT.decode(accessToken.toString());
				Map<String, Claim> data = decode.getClaims();
				if(data != null &&  data.containsKey("oid") && data.containsKey("app_displayname")){
					String oid = data.get("oid").asString();
					String name = data.get("app_displayname").asString();
					byte[] image = adalService.getUserProfilePhoto(accessToken.toString(), oid, name);
					Employee employee = employeeRepository.findOne(oid.toString());
					if(employee != null){
						employee.getEmployeesInfo().setImage(image);
						employeeRepository.save(employee);
					}
				}
			}
			
		}
		return token;
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getCompanyUserMap")
	public String getCompanyUserMap( String access_token){
		return adalService.listUsers(access_token);
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping("/getUserProfilePhoto")
	public String getUserProfilePhoto(String access_token,String uid,String name){
		Encoder encoder = Base64.getEncoder();
		byte[] array = adalService.getUserProfilePhoto(access_token,uid,name);
		return encoder.encodeToString(array); 
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@RequestMapping(value="/uploadOwnPhoto",method=RequestMethod.POST)
	public String uploadOwnPhoto(@RequestParam String access_token,@RequestParam String uid,@RequestParam("file") MultipartFile file){
		Encoder encoder = Base64.getEncoder();
//		byte[] array = adalService.getUserProfilePhoto(access_token,uid,name);
		return ""; 
	}
	
	
}
