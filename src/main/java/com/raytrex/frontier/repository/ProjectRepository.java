package com.raytrex.frontier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.raytrex.frontier.repository.bean.Project;

public interface ProjectRepository extends JpaRepository<Project, String> {
	@Query("SELECT p FROM Project p join p.ownerList owner WHERE owner.uid IN :uidList AND owner.leaveDate is NULL")
	public List<Project> findProjectByProjectOwnerUidList(@Param("uidList") List<String> uidList);

	@Query(value="select distinct project_number from task_owner tos, task t where tos.task_no = t.task_no and tos.uid IN :uidList and tos.leave_date is null",nativeQuery=true)
	public List<String> getProductWithOwnTask(@Param("uidList")List<String> uidList);
}
