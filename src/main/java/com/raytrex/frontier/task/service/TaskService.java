package com.raytrex.frontier.task.service;

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
import com.raytrex.frontier.repository.bean.TaskOwner;
import com.raytrex.frontier.repository.bean.TaskStatus;
import com.raytrex.rpv.repository.bean.OrderList;
import com.raytrex.rpv.repository.bean.OrderListRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private SerialNoRepository serialRepository;
	@Autowired
	private OrderListRepository orderListRepository;

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
			List<TaskOwner> ownerList = task.getTaskOwnerList();
			Iterator<TaskOwner> ownerIt = ownerList.iterator();
			while (ownerIt.hasNext()) {
				TaskOwner owner = ownerIt.next();
				if (owner.getLeaveDate() != null) {
					ownerIt.remove();
				}
			}
			// 只留一筆Task Status
			List<TaskStatus> newTaskStatus = new ArrayList<TaskStatus>();
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
	public Task addOwners(String task_no, List<TaskOwner> task_owner) {
		Task task = taskRepository.findOne(task_no);
		if (task != null) {
			// Task Owner list內有資料就進行比對
			if (task.getTaskOwnerList() != null && !task.getTaskOwnerList().isEmpty()) {
				// 過濾已經在專案中的重複加入的人
				for (TaskOwner owner : task.getTaskOwnerList()) {
					if (owner.getLeaveDate() != null)
						continue; // 已離開專案的人可以被加入
					Iterator<TaskOwner> readyIntoTaskOwnerIt = task_owner.iterator();
					while (readyIntoTaskOwnerIt.hasNext()) {
						TaskOwner readyIntoTask = readyIntoTaskOwnerIt.next();
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

	public Task setTaskStatus(String task_no, TaskStatus task_status) {
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
	public Task addTask(String project_number,String name,String owner_uid){
		OrderList orderList = orderListRepository.findOneByProjectNumber(project_number);
		if(orderList != null){
			SimpleDateFormat sdf = new SimpleDateFormat("YYMMDD");
			SerialNo serialNo = serialRepository.findOne("DP");
			Task task = new Task();
			task.setName(name);
			task.setProjectNumber(orderList.getProjectNumber());
			task.setTaskNo("DP"+sdf.format(new Date())+(serialNo.getCount()+1));
			task.setParentTaskNo(null);
			task.setCustomerId(orderList.getCustomer()+"-"+orderList.getLocation());
//			task.setPermissionId(parent_task.getPermissionId());
			//新增Owner
			TaskOwner owner = new TaskOwner();
			owner.setUid(owner_uid);
			owner.setJoinDate(new Date());
			owner.setTaskNo(task.getTaskNo());
			task.getTaskOwnerList().add(owner);
			
			return taskRepository.save(task);
		}else{
			return null;
		}
	}
	
	public Task addSubtask(String parent_task_no,String name,String owner_uid){
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
			TaskOwner owner = new TaskOwner();
			owner.setUid(owner_uid);
			owner.setJoinDate(new Date());
			owner.setTaskNo(task.getTaskNo());
			task.getTaskOwnerList().add(owner);
			return taskRepository.save(task);
		}else{
			return null;
		}
	}
}
