package com.raytrex.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raytrex.erp.repository.TaskRepository;
import com.raytrex.erp.service.bean.Task;
import com.raytrex.erp.service.bean.Task_Owner;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	public Task addTask(Task task){
		return taskRepository.saveAndFlush(task);
	}
	
	public List<Task> findByProjectNumber(String project_number){
		List<Task> result = taskRepository.findByProjectNumber(project_number);
		return  result;
	}
	
	public Task getTask(String task_id){
		Task task = taskRepository.findOne(task_id);
		
		return task;
	}

}
