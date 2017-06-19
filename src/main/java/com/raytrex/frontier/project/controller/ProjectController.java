package com.raytrex.frontier.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.raytrex.frontier.project.service.ProjectService;
import com.raytrex.frontier.repository.bean.Project;

@RestController
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping("/initProjectFromRPV")
	public String initProjecFromRPV(){
		List<Project> projectList = projectService.initProjectFromRVP();
		Gson gson = new Gson();
		return gson.toJson(projectList);
	}
}
