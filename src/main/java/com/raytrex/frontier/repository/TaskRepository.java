package com.raytrex.frontier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.raytrex.frontier.repository.bean.Task;

public interface TaskRepository extends JpaRepository<Task, String>, JpaSpecificationExecutor<Task>  {
	@Query("SELECT distinct t from Task t join t.taskStatusList ts join t.taskOwnerList to where t.projectNumber = ?1 AND ts.status != 'DELETE'")
	public List<Task> findByProjectNumber(String projectNumber);
	public List<Task> findByParentTaskNo(String task_no);
}
