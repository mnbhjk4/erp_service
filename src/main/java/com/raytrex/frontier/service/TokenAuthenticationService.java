package com.raytrex.frontier.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.context.SpringContextResourceAdapter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.raytrex.frontier.repository.EmployeeRepository;
import com.raytrex.frontier.repository.PermissionRepository;
import com.raytrex.frontier.repository.bean.Employee;
import com.raytrex.frontier.repository.bean.EmployeeRoles;
import com.raytrex.frontier.repository.bean.Permission;


@Service
public class TokenAuthenticationService {
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	static final long EXPIRATIONTIME = 864000000; // 10 days
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	public void addAuthentication(HttpServletResponse res, String username) {
//		String JWT = Jwts.builder().setSubject(username)
//				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)).compact();
//		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}

	public Authentication getAuthentication(HttpServletRequest request) {

		Authentication authiction = SecurityContextHolder.getContext().getAuthentication();
		if(authiction == null || !authiction.isAuthenticated()){
			String token = request.getHeader(HEADER_STRING);
			UsernamePasswordAuthenticationToken auth  = null;
			if (token != null) {
				// parse the token.
				token = token.replace(TOKEN_PREFIX, "").trim();
				DecodedJWT decode = JWT.decode(token);
				Map<String, Claim> data = decode.getClaims();
				if(data != null &&  data.containsKey("oid") && data.containsKey("unique_name")){
					String oid = data.get("oid").asString();
					String name = data.get("unique_name").asString();
					List<Permission> permissionList = new ArrayList<Permission>();
					Employee employee = employeeRepository.findOne(oid);

					for(EmployeeRoles role : employee.getRoleList()){
						List<Permission> tmpPermissionList = permissionRepository.findByRoleId(role.getRole().getRoleId());
						if(tmpPermissionList != null && !tmpPermissionList.isEmpty()){
							permissionList.addAll(tmpPermissionList);
						}
					}
					List<Permission> uidPermissionList = permissionRepository.findByUid(oid);
					permissionList.addAll(uidPermissionList);
					return new UsernamePasswordAuthenticationToken(name, oid, permissionList);
				}	
			}
		}
		

		return authiction;
	}
}

