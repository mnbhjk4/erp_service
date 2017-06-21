package com.raytrex.frontier.task.controller;


import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.raytrex.frontier.repository.bean.Task;
import com.raytrex.frontier.task.service.TaskService;
import com.raytrex.rpv.repository.OrderListRepository;
import com.raytrex.rpv.repository.bean.OrderList;

@RestController
@RequestMapping("/task")
public class TaskController {
	static Logger log = Logger.getLogger(TaskController.class);
	@Autowired
	private TaskService taskService;
	@Autowired
	private OrderListRepository order;
	
	
	/**
	 * 用Project number來取得其Project下所有的Task
	 * @param project_number
	 * @return
	 */
	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping(value="/initTaskFromRPV",method=RequestMethod.GET)
	public String initTaskFromRPV(){
		List<Task> taskList = taskService.initTaskFromRPV();
		Gson gson = new Gson();

		return gson.toJson(taskList);
	}
	
	/**
	 * 用Project number來取得其Project下所有的Task
	 * @param project_number
	 * @return
	 */
	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping(value="/getTaskByProjectNo",method=RequestMethod.POST)
	public String getTaskByProjectNo(@RequestBody String project_number){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX:00").create();
		Map requestJson = gson.fromJson(project_number, Map.class);
		
		LinkedHashMap<Task, List<Task>> tasks = taskService.getTaskByProjectNumber(requestJson.get("project_no").toString());
		
		JsonArray jsonArray = new JsonArray();
		for(Task parentTask : tasks.keySet()){
			JsonObject taskJson = (JsonObject) gson.toJsonTree(parentTask);
			List<Task> subTasks = tasks.get(parentTask);
			taskJson.add("subTaskList", gson.toJsonTree(subTasks));
			jsonArray.add(taskJson);
		}
		return gson.toJson(jsonArray);
	}

}
