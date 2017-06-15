package com.raytrex.frontier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.raytrex.frontier.repository.bean.Task;

public interface TaskRepository extends JpaRepository<Task, String>, JpaSpecificationExecutor<Task>  {
	public List<Task> findByProjectNumber(String projectNumber);
	public List<Task> findByParentTaskNo(String task_no);
}
