package com.raytrex.frontier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raytrex.frontier.repository.bean.Project;

public interface ProjectRepository extends JpaRepository<Project, String> {

}
