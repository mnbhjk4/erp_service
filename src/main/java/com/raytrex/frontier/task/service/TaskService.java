package com.raytrex.frontier.task.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.raytrex.frontier.repository.EmployeeRepository;
import com.raytrex.frontier.repository.ProjectRepository;
import com.raytrex.frontier.repository.SerialNoRepository;
import com.raytrex.frontier.repository.TaskRepository;
import com.raytrex.frontier.repository.bean.Employee;
import com.raytrex.frontier.repository.bean.Project;
import com.raytrex.frontier.repository.bean.SerialNo;
import com.raytrex.frontier.repository.bean.Task;
import com.raytrex.frontier.repository.bean.TaskComment;
import com.raytrex.frontier.repository.bean.TaskOwner;
import com.raytrex.frontier.repository.bean.TaskStatus;
import com.raytrex.rpv.repository.OrderListRepository;
import com.raytrex.rpv.repository.bean.OrderList;
@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private SerialNoRepository serialRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private OrderListRepository orderListRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Task> initTaskFromRPV(){
		List<OrderList> orderList = orderListRepository.findAll();
		for(OrderList order : orderList){
			Project project = projectRepository.findOne(order.getProjectNumber());
			if(project == null) continue;
			if(order.getRaytrexPoDate() != null){
				Task task = new Task();
				task.setProjectNumber(order.getProjectNumber());
				task.setTaskNo(getTaskSerialNo());
				task.setName("Get PO");
				task.setCustomerId(project.getCustomerId());
				//Task Status
				TaskStatus status = new TaskStatus();
				status.setStartDate(new Timestamp(order.getRaytrexPoDate().getTime()));
				status.setDueDate(new Timestamp(order.getRaytrexPoDate().getTime()));
				status.setEndDate(new Timestamp(order.getRaytrexPoDate().getTime()));
				status.setPriority(6);
				status.setTaskNo(task.getTaskNo());
				status.setStatus(TaskStatus.Done);
				status.setDescription(project.getStatusList().get(0).getDescription());
				task.getTaskStatusList().add(status);
				//Task Owner
				TaskOwner taskOwner = new TaskOwner();
				taskOwner.setJoinDate(order.getRaytrexPoDate());
				taskOwner.setTaskNo(task.getTaskNo());
				Employee employee = employeeRepository.findOneByEmpNo(order.getSalesPerson());
				taskOwner.setUid(employee.getUid());
				task.getTaskOwnerList().add(taskOwner);
				taskRepository.save(task);
			}
			boolean started = false;
			
			if(order.getMoveIn() != null){
				Task task = new Task();
				task.setProjectNumber(order.getProjectNumber());
				task.setTaskNo(getTaskSerialNo());
				task.setCustomerId(project.getCustomerId());
				task.setName("Move in");
				//Task Status
				TaskStatus status = new TaskStatus();
				status.setStartDate(new Timestamp(order.getMoveIn().getTime()));
				if(order.getMoveIn().getTime() > System.currentTimeMillis()){//還沒Move in
					status.setAlertDate(new Timestamp(order.getMoveIn().getTime()));
					status.setPriority(1);
					if(started){
						status.setStatus(TaskStatus.NotAction);
					}else{
						status.setStatus(TaskStatus.Progressing);
						started = true;
					}
				}else{ //已經Move in
					status.setEndDate(new Timestamp(order.getInstallHW().getTime()));
					status.setPriority(6);
					status.setStatus(TaskStatus.Done);
				}
				status.setDueDate(new Timestamp(order.getInstallHW().getTime()));

				status.setTaskNo(task.getTaskNo());
				task.getTaskStatusList().add(status);
				taskRepository.save(task);
			}
			
			if(order.getInstallHW() != null){
				Task task = new Task();
				task.setProjectNumber(order.getProjectNumber());
				task.setTaskNo(getTaskSerialNo());
				task.setCustomerId(project.getCustomerId());
				task.setName("Install Hardware");
				//Task Status
				TaskStatus status = new TaskStatus();
				status.setStartDate(new Timestamp(order.getInstallHW().getTime()));
				if(order.getInstallHW().getTime() > System.currentTimeMillis()){//還沒Install HW
					status.setAlertDate(new Timestamp(order.getInstallSW().getTime()));
					status.setPriority(2);
					if(started){
						status.setStatus(TaskStatus.NotAction);
					}else{
						status.setStatus(TaskStatus.Progressing);
						started = true;
					}
				}else{ //已經Install HW
					status.setEndDate(new Timestamp(order.getInstallSW().getTime()));
					status.setPriority(6);
					status.setStatus(TaskStatus.Done);
				}
				status.setDueDate(new Timestamp(order.getInstallSW().getTime()));
				
				status.setTaskNo(task.getTaskNo());
				task.getTaskStatusList().add(status);
				
				taskRepository.save(task);
			}
			
			if(order.getInstallSW() != null){
				Task task = new Task();
				task.setProjectNumber(order.getProjectNumber());
				task.setTaskNo(getTaskSerialNo());
				task.setCustomerId(project.getCustomerId());
				task.setName("Install Software");
				//Task Status
				TaskStatus status = new TaskStatus();
				status.setStartDate(new Timestamp(order.getInstallSW().getTime()));
				if(order.getInstallSW().getTime() > System.currentTimeMillis()){//還沒Move in
					status.setAlertDate(new Timestamp(order.getInstallMeasure().getTime()));
					status.setPriority(3);
					if(started){
						status.setStatus(TaskStatus.NotAction);
					}else{
						status.setStatus(TaskStatus.Progressing);
						started = true;
					}
				}else{ //已經Move in
					status.setEndDate(new Timestamp(order.getInstallMeasure().getTime()));
					status.setPriority(6);
					status.setStatus(TaskStatus.Done);
				}
				status.setDueDate(new Timestamp(order.getInstallMeasure().getTime()));
				status.setTaskNo(task.getTaskNo());
				task.getTaskStatusList().add(status);
				
				taskRepository.save(task);
			}
			
			if(order.getInstallMeasure() != null){
				Task task = new Task();
				task.setProjectNumber(order.getProjectNumber());
				task.setTaskNo(getTaskSerialNo());
				task.setCustomerId(project.getCustomerId());
				task.setName("Install Measure");
				//Task Status
				TaskStatus status = new TaskStatus();
				status.setStartDate(new Timestamp(order.getInstallMeasure().getTime()));
				if(order.getInstallMeasure().getTime() > System.currentTimeMillis()){//還沒Install measure
					status.setAlertDate(new Timestamp(order.getUat().getTime()));
					status.setPriority(3);
					if(started){
						status.setStatus(TaskStatus.NotAction);
					}else{
						status.setStatus(TaskStatus.Progressing);
						started = true;
					}
				}else{ //已經還沒Install measure
					status.setEndDate(new Timestamp(order.getUat().getTime()));
					status.setPriority(6);
					status.setStatus(TaskStatus.Done);
				}
				status.setDueDate(new Timestamp(order.getUat().getTime()));
				
				status.setTaskNo(task.getTaskNo());
				task.getTaskStatusList().add(status);
				
				taskRepository.save(task);
			}
			
			if(order.getUat() != null){
				Task task = new Task();
				task.setProjectNumber(order.getProjectNumber());
				task.setTaskNo(getTaskSerialNo());
				task.setCustomerId(project.getCustomerId());
				task.setName("UAT");
				//Task Status
				TaskStatus status = new TaskStatus();
				status.setStartDate(new Timestamp(order.getUat().getTime()));
				if(order.getUat().getTime() > System.currentTimeMillis()){//還沒UAT
					status.setAlertDate(new Timestamp(order.getFat().getTime()));
					status.setPriority(4);
					if(started){
						status.setStatus(TaskStatus.NotAction);
					}else{
						status.setStatus(TaskStatus.Progressing);
						started = true;
					}
				}else{ //已經還沒UAT
					status.setEndDate(new Timestamp(order.getFat().getTime()));
					status.setPriority(6);
					status.setStatus(TaskStatus.Done);
				}
				status.setDueDate(new Timestamp(order.getFat().getTime()));
				status.setTaskNo(task.getTaskNo());
				task.getTaskStatusList().add(status);
				
				taskRepository.save(task);
			}
			
			if(order.getFat() != null){
				Task task = new Task();
				task.setProjectNumber(order.getProjectNumber());
				task.setTaskNo(getTaskSerialNo());
				task.setCustomerId(project.getCustomerId());
				task.setName("FAT");
				//Task Status
				TaskStatus status = new TaskStatus();
				status.setStartDate(new Timestamp(order.getFat().getTime()));
				if(order.getFat().getTime() > System.currentTimeMillis()){//還沒FAT
					status.setAlertDate(new Timestamp(order.getFat().getTime()));
					status.setPriority(4);
					if(started){
						status.setStatus(TaskStatus.NotAction);
					}else{
						status.setStatus(TaskStatus.Progressing);
						started = true;
					}
				}else{ //已經還沒FAT
					status.setEndDate(new Timestamp(order.getFat().getTime()));
					status.setPriority(6);
					status.setStatus(TaskStatus.Done);
				}
				status.setDueDate(new Timestamp(order.getFat().getTime()));
				status.setTaskNo(task.getTaskNo());
				task.getTaskStatusList().add(status);
				
				taskRepository.save(task);
			}
		}
		
		List<Task> taskList = taskRepository.findAll();
		return taskList;
	}
	
	
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
	public LinkedHashMap<Task, List<Task>> getTaskByProjectNumber(String project_number,List<String> memeberUidList) {
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
			boolean ownTask = false;
			while (ownerIt.hasNext()) {
				TaskOwner owner = ownerIt.next();
				if (owner.getLeaveDate() != null) {
					ownerIt.remove();
					continue;
				}
				if(memeberUidList.contains(owner.getUid())){
					ownTask = true;
				}
			}
			if(!ownTask) continue;
			// 只留一筆Task Status
			List<TaskStatus> newTaskStatus = new ArrayList<TaskStatus>();
			newTaskStatus.add(task.getTaskStatusList().get(0));
			task.setTaskStatusList(newTaskStatus);
		}
		return resultMap;
	}

	/**
	 * 在指定的Task no中新增/修改 一筆或多筆的Task owner (Join task)
	 * @param task_no
	 * @param task_owner
	 * @return 更新後的Task內容
	 */
	public Task addOwners(String task_no, List<TaskOwner> task_owner) {
		Task task = taskRepository.findOne(task_no); //找到原本的Task
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
			if (task_status.getTaskNo() == null) {
				task_status.setTaskNo(task.getTaskNo());
			}
			task.getTaskStatusList().add(task_status);
			task = taskRepository.saveAndFlush(task);
		}
		return task;
	}
	public Task addTask(String project_number,String name,String owner_uid){
		Project project = projectRepository.findOne(project_number);
		if(project != null){
			Task task = new Task();
			task.setName(name);
			task.setProjectNumber(project.getProjectNo());
			task.setTaskNo(getTaskSerialNo());
			task.setParentTaskNo(null);
			task.setCustomerId(project.getCustomerId());
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
		if(parent_task != null){
			Task task = new Task();
			task.setName(name);
			task.setProjectNumber(parent_task.getProjectNumber());
			task.setTaskNo(getTaskSerialNo());
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
	
	private String getTaskSerialNo(){
		SimpleDateFormat sdf = new SimpleDateFormat("YYMMDD");
		DecimalFormat df = new DecimalFormat("000");
		SerialNo serialNo = serialRepository.findOne("DP");
		if(serialNo == null){
			SerialNo sn = new SerialNo();
			sn.setCount(0);
			sn.setSerialName("DP");
			sn.setDescription("工作派工單序號");
			serialNo = serialRepository.save(sn);
		}
		Integer count = serialNo.getCount()+1;
		serialNo.setCount(count);
		String no = "DP"+sdf.format(new Date())+ df.format(count);
		serialRepository.save(serialNo);
		return no;
	}


	public void saveNewTask(Task task) {
		String serialNo = getTaskSerialNo();
		task.setTaskNo(serialNo);
		if(task.getTaskCommentList() != null && !task.getTaskCommentList().isEmpty()){
			for(TaskComment comment : task.getTaskCommentList()){
				comment.setTaskNo(serialNo);
			}
		}
		if(task.getTaskOwnerList() != null && !task.getTaskOwnerList().isEmpty()){
			for(TaskOwner owner : task.getTaskOwnerList()){
				owner.setTaskNo(serialNo);
			}
		}
		if(task.getTaskStatusList() != null && !task.getTaskStatusList().isEmpty()){
			for(TaskStatus status : task.getTaskStatusList()){
				status.setTaskNo(serialNo);
			}
		}
		taskRepository.save(task);
	}


	public Task getTask(String taskNo) {
		return taskRepository.findOne(taskNo);
	}


	public void updateTask(Task subTask) {
		// TODO Auto-generated method stub
		
	}
	
	public Task save(Task task){
		Task dbTask = taskRepository.findOne(task.getTaskNo());//先找看看是否有相同的Task No物件
		if(dbTask == null){//是一筆新的Task
			dbTask = task; 
			dbTask.setTaskNo(getTaskSerialNo());//給予新的Task No
		}
		
		//開始Merge attribute
		if(dbTask.getAttachUuid() != task.getAttachUuid() && task.getAttachUuid() != null && !task.getAttachUuid().isEmpty()){
			dbTask.setAttachUuid(task.getAttachUuid());
		}
		if(dbTask.getCustomerId() != task.getCustomerId() && task.getCustomerId() != null && !task.getCustomerId().isEmpty()){
			dbTask.setCustomerId(task.getCustomerId());
		}
		if(dbTask.getName() != task.getName() && task.getName() != null && !task.getName().isEmpty()){
			dbTask.setName(task.getName());
		}
		if(dbTask.getParentTaskNo() != task.getParentTaskNo()){
			dbTask.setParentTaskNo(task.getParentTaskNo());
		}
		if(dbTask.getPermissionId() != task.getPermissionId() && task.getPermissionId() != null && !task.getPermissionId().isEmpty()){
			dbTask.setPermissionId(task.getPermissionId());
		}
		if(dbTask.getProjectNumber() != task.getProjectNumber() && task.getProjectNumber() != null && !task.getProjectNumber().isEmpty()){
			dbTask.setProjectNumber(task.getProjectNumber());
		}
		//Merge Task Owner
		LinkedHashMap<String,TaskOwner> taskOwnerMap = new LinkedHashMap<String,TaskOwner>();//加入名單
		HashSet<String> leaveUidSet = new HashSet<String>();//剔除名單(要準備Update Leave date)
		for(TaskOwner dbOwner : dbTask.getTaskOwnerList()){
			if(dbOwner.getLeaveDate() == null){
				taskOwnerMap.put(dbOwner.getUid(), dbOwner);
				leaveUidSet.add(dbOwner.getUid());
			}
		}
		for(TaskOwner owner : task.getTaskOwnerList()){
			if(taskOwnerMap.containsKey(owner.getUid())){
				leaveUidSet.remove(owner.getUid()); //如果有重複的就update,並從剃除名單中移除
			}else{
				dbTask.getTaskOwnerList().add(owner); //加入名單
			}
			if(owner.getJoinDate() == null){
				owner.setJoinDate(new Date(System.currentTimeMillis()));
			}
		}
		for(String uid : leaveUidSet){//開始將剃除名單中Update Leave date
			taskOwnerMap.get(uid).setLeaveDate(new Date());
		}
		//Merge Task status
		TaskStatus dbTaskStatus = dbTask.getTaskStatusList().get(0); //與最近一筆進行Compare
		TaskStatus taskStatus = task.getTaskStatusList().get(0);
		boolean addStatus = false;
		if((dbTaskStatus.getAlertDate() == null && taskStatus.getAlertDate() != null) || 
				(dbTaskStatus.getAlertDate() != null && taskStatus.getAlertDate() != null && dbTaskStatus.getAlertDate().getTime() != taskStatus.getAlertDate().getTime())){
			addStatus = true;
		}
		if((dbTaskStatus.getDueDate() == null && taskStatus.getDueDate() != null) || 
				(dbTaskStatus.getDueDate() != null && taskStatus.getDueDate() != null && dbTaskStatus.getDueDate().getTime() != taskStatus.getDueDate().getTime())){
			addStatus = true;
		}
		if((dbTaskStatus.getStartDate() == null && taskStatus.getStartDate() != null) || 
				(dbTaskStatus.getStartDate() != null && taskStatus.getStartDate() != null && dbTaskStatus.getStartDate().getTime() != taskStatus.getStartDate().getTime())){
			addStatus = true;
		}
		if((dbTaskStatus.getEndDate() == null && taskStatus.getEndDate() != null) || 
				(dbTaskStatus.getEndDate() != null && taskStatus.getEndDate() != null && dbTaskStatus.getEndDate().getTime() != taskStatus.getEndDate().getTime())){
			addStatus = true;
		}
		if((dbTaskStatus.getDescription() == null && taskStatus.getDescription() != null) || 
				(dbTaskStatus.getDescription() != null && taskStatus.getDescription() != null && !dbTaskStatus.getDescription().equals(taskStatus.getDescription()))){
			addStatus = true;
		}
		if((dbTaskStatus.getParentTaskNo() == null && taskStatus.getParentTaskNo() != null) || 
				(dbTaskStatus.getParentTaskNo() != null && taskStatus.getParentTaskNo() != null && !dbTaskStatus.getParentTaskNo().equals(taskStatus.getParentTaskNo()))){
			addStatus = true;
		}

		if(dbTaskStatus.getPriority() != taskStatus.getPriority()){
			addStatus = true;
		}
		if((dbTaskStatus.getStatus() == null && taskStatus.getStatus() != null) || 
				(dbTaskStatus.getStatus() != null && taskStatus.getStatus() != null && !dbTaskStatus.getStatus().equals(taskStatus.getStatus()))){
			addStatus = true;
		}
		if(dbTaskStatus.getTaskIndex() != taskStatus.getTaskIndex()){
			addStatus = true;
		}
		if(addStatus){
			task.getTaskStatusList().get(0).setTaskStatusId(null);//將key移除
			dbTask.getTaskStatusList().add(task.getTaskStatusList().get(0));
		}
		//Merge Task Comment
		List<TaskComment> newComment = new ArrayList<TaskComment>();
		for(TaskComment comment : task.getTaskCommentList()){
			boolean isFound = false;
			for(TaskComment dbComment : dbTask.getTaskCommentList()){
				if(comment.getTaskCommentUuid().equals(dbComment.getTaskCommentUuid()) || comment.getComment().equals(dbComment.getComment())){
					isFound = true;
				}
			}
			if(!isFound){
				dbTask.getTaskCommentList().add(comment);
			}
		}
		
		return taskRepository.save(dbTask);
	}
}
