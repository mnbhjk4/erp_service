package com.raytrex.erp.service;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.raytrex.erp.service.bean.Employees;
import com.raytrex.erp.service.bean.Employees_Info;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public Employees getEmployeesJDBC(EmployeesRepository r){
    	Employees e = new Employees();
    	Employees_Info info = new Employees_Info();
    	info.setBirth_date(new Date());
    	info.setFirst_name("TEST");
    	e.setEmp_no("Test");
    	e.setMail("test@raytrex.com");
    	e.setEmployees_Info(info);
    	
    	r.save(e); 
    	
    	return e;
    }
    
  
}
