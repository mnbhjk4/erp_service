package com.raytrex.frontier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.raytrex.frontier.repository.bean.ProjectStatus;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Integer> {

}
