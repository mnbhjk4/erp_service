package com.raytrex.frontier.task.controller;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import com.raytrex.frontier.repository.bean.Quotation;
import com.raytrex.frontier.repository.bean.Task;
import com.raytrex.frontier.task.service.QuotationService;
import com.raytrex.frontier.task.service.TaskService;
import com.raytrex.frontier.utils.GsonUtil;

@RestController
@RequestMapping("/task")
public class TaskController {
	static Logger log = Logger.getLogger(TaskController.class);
	@Autowired
	private TaskService taskService;
	@Autowired
	private QuotationService quotationService;
	
	
	/**
	 * 用Project number來取得其Project下所有的Task
	 * @param project_number
	 * @return
	 */
	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping(value="/initTaskFromRPV",method=RequestMethod.GET)
	public String initTaskFromRPV(){
		List<Task> taskList = taskService.initTaskFromRPV();
		Gson gson = GsonUtil.getGson();

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
		Gson gson = GsonUtil.getGson();
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

	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping(value="/saveTask",method=RequestMethod.POST)
	public String saveTask(@RequestBody String taskJson){
		Gson gson = GsonUtil.getGson();
		Task refreshTask = null;
		try{
			Task task = gson.fromJson(taskJson, Task.class); //取得母Task類別
			
			if(!task.getSubTaskList().isEmpty()){
				for(Task subTask : task.getSubTaskList()){
					if(subTask.getType().equals("NORMAL_TASK")){
						taskService.save(subTask);
					}else if(subTask.getType().equals("QUOTATION_TASK")){
						if(subTask.getQuotation() != null){
							Quotation q = subTask.getQuotation();
							if(q.getQuotationNo() == null || q.getQuotationNo().isEmpty()){
								q.setQuotationNo(quotationService.getQuotationNo());
							}
						}
						subTask.setName(subTask.getQuotation().getQuotationNo());
						taskService.save(subTask);
						Quotation newQ = quotationService.saveQuotation(subTask.getQuotation());
						quotationService.saveQuotationItem(newQ,subTask.getQuotationItemList());
					}
					
				}	
			}
			refreshTask = taskService.save(task);
		}catch(Exception e){
			log.error("Orgion taskJson = "+taskJson,e);
			return gson.toJson("Error: Gson parser is failed to parse Task,please confirm with IT."+e.getMessage());
		}
		
		if(refreshTask != null){
			return gson.toJson(refreshTask);
		}else{
			return gson.toJson("Error: Task should be update and found but it's gone.");
		}
		
	}
}
