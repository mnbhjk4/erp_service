package com.raytrex.erp.contorller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.raytrex.erp.service.TaskService;
import com.raytrex.erp.service.bean.Task;
import com.raytrex.erp.service.bean.Task_Owner;

@RestController
@RequestMapping("/task")
public class TaskController {
	static Logger log = Logger.getLogger(TaskController.class);
	@Autowired
	private TaskService taskService;

	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping("/test")
	public String test(HttpServletRequest request) {
		if(request != null){
			log.info(request.getParameterMap().toString());
		}
		Gson gson = new Gson();
		List<Task> task = taskService.findByProjectNumber("b40bde23-8d06-4b65-8f46-262bfe3c635a");
		return gson.toJson(task);
	}
}
