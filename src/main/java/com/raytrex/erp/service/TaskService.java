package com.raytrex.erp.service;

import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.raytrex.erp.repository.TaskRepository;
import com.raytrex.erp.service.bean.Task;
import com.raytrex.erp.service.bean.Task_Owner;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	
	/**
	 * 用Project number來取得全部的Task與其Sub task
	 * @param project_number
	 * @return
	 */
	public List<Task> getTaskByProjectNumber(String project_number){
		return taskRepository.findByProjectNumber(project_number);
	}
	
	public Task addOwners(String task_no,List<Task_Owner> task_owner){

		Task task = taskRepository.findOne(new Specification<Task>() {

			@Override
			public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Root<Task_Owner> taskOwnerList = query.from(Task_Owner.class);
				Path<Object> leaveDate = taskOwnerList.get("leaveDate");
				Predicate p = leaveDate.isNull();
				return cb.and(p);
			}
		});
		if(task != null){
			//Task Owner list內有資料就進行比對
			if(task.getTaskOwnerList() != null && !task.getTaskOwnerList().isEmpty()){
				//過濾已經在專案中的重複加入的人
				for(Task_Owner owner : task.getTaskOwnerList()){
					if(owner.getLeaveDate() != null) continue; //已離開專案的人可以被加入
					Iterator<Task_Owner> readyIntoTaskOwnerIt = task_owner.iterator();
					while(readyIntoTaskOwnerIt.hasNext()){
						Task_Owner readyIntoTask = readyIntoTaskOwnerIt.next();
						if(readyIntoTask.getUid().equals(owner.getUid())){
							readyIntoTaskOwnerIt.remove();
						}
					}
				}
			}
		}
		return task;
	}
}
