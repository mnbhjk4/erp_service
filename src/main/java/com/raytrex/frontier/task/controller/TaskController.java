package com.raytrex.frontier.task.controller;


import java.util.LinkedHashMap;
import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
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
	@RequestMapping(value="/getTaskByProject",method=RequestMethod.POST)
	public String getTaskByProject(@RequestBody String project_number){
		LinkedHashMap<Task, List<Task>> tasks = taskService.getTaskByProjectNumber(project_number);
		Gson gson = new Gson();
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
