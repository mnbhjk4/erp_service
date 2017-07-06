package com.raytrex.frontier.project.controller;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.raytrex.frontier.project.service.ProjectService;
import com.raytrex.frontier.repository.bean.Project;
import com.raytrex.frontier.repository.bean.Task;
import com.raytrex.frontier.task.service.TaskService;
import com.raytrex.frontier.utils.GsonUtil;

@RestController
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private TaskService taskService;
	
	@RequestMapping("/initProjectFromRPV")
	public String initProjecFromRPV(){
		List<Project> projectList = projectService.initProjectFromRVP();
		Gson gson = new Gson();
//		return gson.toJson("Please unmark init project from RPV porject this is important!");
		return gson.toJson(projectList);
	}
	
	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping(value="/geProjectByUid",method=RequestMethod.POST)
	public String geProjectByUid(HttpServletRequest request){
		String body = "";
		Properties p = new Properties();
		try {
			body = IOUtils.toString(request.getInputStream(),Charset.forName("UTF8"));
			
			p.load(new StringReader(body));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = GsonUtil.getGson();
		JsonArray results = new JsonArray();
		if(p.containsKey("uid")){
			List<Project> projectList = projectService.getProjectByUid(p.getProperty("uid"));
			if(!projectList.isEmpty()){
				projectList = projectService.sortProjectList(projectList);
			}
			for(Project project : projectList){
				LinkedHashMap<Task, List<Task>> tasks = taskService.getTaskByProjectNumber(project.getProjectNo());
				
				JsonArray jsonArray = new JsonArray();
				for(Task parentTask : tasks.keySet()){
					JsonObject taskJson = (JsonObject) gson.toJsonTree(parentTask);
					List<Task> subTasks = tasks.get(parentTask);
					taskJson.add("subTaskList", gson.toJsonTree(subTasks));
					jsonArray.add(taskJson);
				}
				JsonObject object = new JsonObject();
				object.add("project", gson.toJsonTree(project));
				object.add("task",jsonArray);
				results.add(object);
			}
		}
		return gson.toJson(results);
	}
}
