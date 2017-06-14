package com.raytrex.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.erp.service.bean.Task;

public interface TaskRepository extends JpaRepository<Task, String> {
	public List<Task> findByProjectNumber(String projectNumber);
}
