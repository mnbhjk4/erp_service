package com.raytrex.erp;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScans;

import com.raytrex.erp.service.bean.Employees;
import com.raytrex.erp.service.bean.Employees_Info;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    
  
}
