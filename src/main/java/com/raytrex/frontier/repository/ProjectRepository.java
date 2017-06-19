package com.raytrex.frontier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.raytrex.frontier.repository.bean.Project;

public interface ProjectRepository extends JpaRepository<Project, String> {
	@Query("SELECT p FROM Project p join p.ownerList owner WHERE owner.uid = ?1")
	public List<Project> findProjectByProjectOwnerUid(String uid);

}
