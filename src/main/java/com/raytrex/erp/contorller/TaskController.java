package com.raytrex.erp.contorller;


import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.raytrex.erp.service.TaskService;
import com.raytrex.erp.service.bean.Task;

@RestController
@RequestMapping("/task")
public class TaskController {
	static Logger log = Logger.getLogger(TaskController.class);
	@Autowired
	private TaskService taskService;

	@CrossOrigin(origins = { "*", "http://localhost:8100" })
	@RequestMapping(value="/test",method=RequestMethod.POST)
	public String test(@RequestBody String uid) {
	
		Gson gson = new Gson();
//		List<Task> task = taskService.findByProjectNumber("RX0000207");
		return gson.toJson(null);
	}
}
