package com.raytrex.erp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raytrex.frontier.repository.SerialNoRepository;
import com.raytrex.frontier.repository.TaskRepository;
import com.raytrex.frontier.repository.bean.SerialNo;
import com.raytrex.frontier.repository.bean.Task;
import com.raytrex.frontier.repository.bean.Task_Owner;
import com.raytrex.frontier.repository.bean.Task_Status;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private SerialNoRepository serialRepository;

	/**
	 * 用Project number來取得全部的Task與其Sub task<br>
	 * @param project_number Project Number
	 * @return 回傳LinkedHashMap架構如下圖:<br>
	 * <ul>
	 * 	<li>Parent Task1</li>
	 * 	<ul>
	 * 		<li>Child Task1</li>
	 * 		<li>Child Task2</li>
	 * 		<li>Child Task3</li>
	 * 	</ul>
	 * 	<li>Parent Task2</li>
	 * 	<ul>
	 * 		<li>Child Task1</li>
	 * 		<li>Child Task2</li>
	 * 		<li>Child Task3</li>
	 * 	</ul>
	 * </ul>
	 */
	public LinkedHashMap<Task, List<Task>> getTaskByProjectNumber(String project_number) {
		LinkedHashMap<Task, List<Task>> resultMap = new LinkedHashMap<Task, List<Task>>();
		List<Task> result = taskRepository.findByProjectNumber(project_number);
		//先將Task的Root task放到Map中
		for (Task task : result) {
			if (task.getParentTaskNo() == null || task.getParentTaskNo().isEmpty()) {
				resultMap.put(task, new ArrayList<Task>());
			}
		}
		//過濾不需要的資料內容
		for (Task task : result) {
			//嘗試去找尋Task群駔
			if(task.getParentTaskNo() != null && !task.getParentTaskNo().isEmpty()){
				for(Task t : resultMap.keySet()){
					if(t.getTaskNo().equals(task.getParentTaskNo())){
						resultMap.get(t).add(task);
					}
				}
			}
			
			// 移除已經不在Task中的Owner
			List<Task_Owner> ownerList = task.getTaskOwnerList();
			Iterator<Task_Owner> ownerIt = ownerList.iterator();
			while (ownerIt.hasNext()) {
				Task_Owner owner = ownerIt.next();
				if (owner.getLeaveDate() != null) {
					ownerIt.remove();
				}
			}
			// 只留一筆Task Status
			List<Task_Status> newTaskStatus = new ArrayList<Task_Status>();
			newTaskStatus.add(task.getTaskStatusList().get(0));
			task.setTaskStatusList(newTaskStatus);
		}
		return resultMap;
	}

	/**
	 * 在指定的Task no中新增一筆或多筆的Task owner (Join task)
	 * @param task_no
	 * @param task_owner
	 * @return 更新後的Task內容
	 */
	public Task addOwners(String task_no, List<Task_Owner> task_owner) {
		Task task = taskRepository.findOne(task_no);
		if (task != null) {
			// Task Owner list內有資料就進行比對
			if (task.getTaskOwnerList() != null && !task.getTaskOwnerList().isEmpty()) {
				// 過濾已經在專案中的重複加入的人
				for (Task_Owner owner : task.getTaskOwnerList()) {
					if (owner.getLeaveDate() != null)
						continue; // 已離開專案的人可以被加入
					Iterator<Task_Owner> readyIntoTaskOwnerIt = task_owner.iterator();
					while (readyIntoTaskOwnerIt.hasNext()) {
						Task_Owner readyIntoTask = readyIntoTaskOwnerIt.next();
						if (readyIntoTask.getUid().equals(owner.getUid())) {
							readyIntoTaskOwnerIt.remove();
						}
					}
				}
			}
			task.getTaskOwnerList().addAll(task_owner);
			task = taskRepository.saveAndFlush(task);
		}
		return task;
	}

	public Task setTaskStatus(String task_no, Task_Status task_status) {
		Task task = taskRepository.findOne(task_no);
		if (task != null) {
			// 確認資料Task status是否正確
			if (task_status.getTask_no() == null) {
				task_status.setTask_no(task.getTaskNo());
			}
			task.getTaskStatusList().add(task_status);
			task = taskRepository.saveAndFlush(task);
		}
		return task;
	}
	public Task addTask(String project_number,String name){
		SimpleDateFormat sdf = new SimpleDateFormat("YYMMDD");
		if(!name.isEmpty()){
			SerialNo serialNo = serialRepository.findOne("DP");
			Task task = new Task();
			task.setName(name);
			task.setProjectNumber(project_number);
			task.setTaskNo("DP"+sdf.format(new Date())+(serialNo.getCount()+1));
			task.setParentTaskNo(parent_task_no);
			task.setCustomerId(parent_task.getCustomerId());
			task.setPermissionId(parent_task.getPermissionId());
			return taskRepository.save(task);
		}else{
			return null;
		}
	}
	
	public Task addSubtask(String parent_task_no,String name){
		Task parent_task = taskRepository.findOne(parent_task_no);
		SimpleDateFormat sdf = new SimpleDateFormat("YYMMDD");
		if(parent_task != null){
			SerialNo serialNo = serialRepository.findOne("DP");
			Task task = new Task();
			task.setName(name);
			task.setProjectNumber(parent_task.getProjectNumber());
			task.setTaskNo("DP"+sdf.format(new Date())+(serialNo.getCount()+1));
			task.setParentTaskNo(parent_task_no);
			task.setCustomerId(parent_task.getCustomerId());
			task.setPermissionId(parent_task.getPermissionId());
			return taskRepository.save(task);
		}else{
			return null;
		}
	}
}
