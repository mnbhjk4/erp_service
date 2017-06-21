package com.raytrex.frontier.project.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raytrex.frontier.repository.CustomerRepository;
import com.raytrex.frontier.repository.EmployeeRepository;
import com.raytrex.frontier.repository.ProjectRepository;
import com.raytrex.frontier.repository.bean.Customer;
import com.raytrex.frontier.repository.bean.Employee;
import com.raytrex.frontier.repository.bean.Project;
import com.raytrex.frontier.repository.bean.ProjectOwner;
import com.raytrex.frontier.repository.bean.ProjectStatus;
import com.raytrex.rpv.repository.OrderListRepository;
import com.raytrex.rpv.repository.bean.OrderList;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRespository;
	@Autowired
	private OrderListRepository orderListRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * 從RPV那邊Import全部的Project到Frontier
	 * @return
	 */
	public List<Project> initProjectFromRVP(){
		//先從Order list中取得全部目前的Project
		List<OrderList> oerderList = orderListRepository.findAll();
		//再從Frontier
		List<Customer> customerList = customerRepository.findAll();
		
		for(OrderList order : oerderList){
			Project project = projectRespository.findOne(order.getProjectNumber());
			if(project == null){
				 project = new Project();
			}
			project.setProjectNo(order.getProjectNumber());
			
			String orderCustomer = order.getCustomer();
			String orderCustomerLocal = order.getLocation();
			Customer targetCustomer = null;
			for(Customer customer : customerList){
				if(orderCustomer.indexOf(customer.getName()) != -1){
					//因為RPV2的預設值好像都是空白字串所以特別拿出來處理
					if(orderCustomerLocal == null || orderCustomerLocal.isEmpty()){
						if(customer.getRegion() == null){
							targetCustomer = customer;
							break;
						}
					}else{
						if(customer.getRegion().equals(orderCustomerLocal)){
							targetCustomer = customer;
							break;
						}
					}
				}
			}
			if(targetCustomer != null){
				project.setCustomerId(targetCustomer.getCustomerId());
			}
			//Sales
			if(order.getSalesPerson() != null && !order.getSalesPerson().isEmpty()){
				Employee sales = employeeRepository.findOneByEmpNo(order.getSalesPerson());
				if(sales != null){
					ProjectOwner owner = new ProjectOwner();
					owner.setUid(sales.getUid());
					owner.setProjectNo(project.getProjectNo());
					owner.setJoinDate(order.getRaytrexPoDate());
					project.getOwnerList().add(owner);
				}				
			}
			//Engineer
			if(order.getEngineer() != null && !order.getEngineer().isEmpty()){
				Employee engineer = employeeRepository.findOneByEmpNo(order.getEngineer());
				if(engineer != null){
					ProjectOwner owner = new ProjectOwner();
					owner.setUid(engineer.getUid());
					owner.setProjectNo(project.getProjectNo());
					owner.setJoinDate(order.getMoveIn());
					project.getOwnerList().add(owner);
				}			
			}
			
			//加上AGI
			Employee engineer = employeeRepository.findOneByEmpNo("7f8ee838-ff01-4b7a-828b-37c1541919b0");
			if(engineer != null){
				ProjectOwner owner = new ProjectOwner();
				owner.setUid(engineer.getUid());
				owner.setProjectNo(project.getProjectNo());
				owner.setJoinDate(order.getMoveIn());
				project.getOwnerList().add(owner);
			}		
			//初始Status
			ProjectStatus projectStatus = new ProjectStatus();
			String projectName = "";
			if(targetCustomer != null){
				projectName += targetCustomer.getName();
				if(targetCustomer.getRegion() != null && !targetCustomer.getRegion().isEmpty()){
					projectName += "-"+targetCustomer.getRegion()+"\n";
				}
				projectName+= "\n"+order.getModel();
			}else{
				projectName+= order.getProjectNumber();
			}
			
			projectStatus.setProjectName(projectName);
			projectStatus.setProjectNo(project.getProjectNo());
			projectStatus.setStartDate(order.getRaytrexPoDate());
			projectStatus.setDescription(order.getConfig());
			//依照階段來分級Project的Priority
			if(order.getMoveIn() != null){
				if(order.getMoveIn().getTime() > System.currentTimeMillis()){//還沒完成
					projectStatus.setPriority(1);
					projectStatus.setAlarmDate(order.getMoveIn());
				}
			}
			if(order.getInstallHW() != null){
				if(order.getInstallHW().getTime() > System.currentTimeMillis() && projectStatus.getPriority() > 1){//還沒完成
					projectStatus.setPriority(2);
					projectStatus.setAlarmDate(order.getInstallHW());
				}
			}
			if(order.getInstallSW() != null){
				if(order.getInstallSW().getTime() > System.currentTimeMillis() && projectStatus.getPriority() > 2){//還沒完成
					projectStatus.setPriority(3);
					projectStatus.setAlarmDate(order.getInstallSW());
				}
			}
			if(order.getInstallMeasure() != null){
				if(order.getInstallMeasure().getTime() > System.currentTimeMillis() && projectStatus.getPriority() > 3){//還沒完成
					projectStatus.setPriority(4);
					projectStatus.setAlarmDate(order.getInstallMeasure());
				}
			}
			if(order.getUat() != null){
				if(order.getUat().getTime() > System.currentTimeMillis() && projectStatus.getPriority() > 4){//還沒完成
					projectStatus.setPriority(5);
					projectStatus.setAlarmDate(order.getUat());
				}
			}
			if(order.getFat() != null){
				if(order.getFat().getTime() > System.currentTimeMillis() && projectStatus.getPriority() > 5){//還沒完成
					projectStatus.setPriority(3);
					projectStatus.setAlarmDate(order.getFat());
				}
				if(order.getFat().getTime() < System.currentTimeMillis()){//已經超過時間就結束Project
					projectStatus.setEndDate(order.getFat());
				}
				projectStatus.setDueDate(order.getFat());//Project預計結束的時間
				
			}
			
			projectStatus.setStatusUuid(UUID.randomUUID().toString());

			project.getStatusList().add(projectStatus);
			
			projectRespository.save(project);
		}
		return projectRespository.findAll();
	}
	
	public List<Project> getProjectByUid(String uid){
		return projectRespository.findProjectByProjectOwnerUid(uid);
	}
	
	public List<Project> sortProjectList(List<Project> src){
		Project [] projectArray = new Project[src.size()];
		src.toArray(projectArray);
		for(int f = 0; f < projectArray.length - 1; f++){
			for(int s = 0; s < projectArray.length -f- 1; s++){
				ProjectStatus target = projectArray[s].getStatusList().get(0);
				ProjectStatus compare = projectArray[s+1].getStatusList().get(0);
				boolean change = false;
				if(compare.getPriority() < target.getPriority()){
					change = true;
				}else if( compare.getAlarmDate() != null && target.getAlarmDate() == null){
					change = true;
				}else if((compare.getAlarmDate() != null && target.getAlarmDate() != null) &&
						compare.getAlarmDate().getTime() < target.getAlarmDate().getTime()){
					change = true;
				}
//				else if( compare.getDueDate() != null && target.getDueDate() == null){
//					change = true;
//				}else if((compare.getDueDate() != null && target.getDueDate() != null) &&
//						compare.getDueDate().getTime() < target.getDueDate().getTime()){
//					change = true;
//				}
				
				if(change){
					Project tempProject = projectArray[s+1];
					projectArray[s+1] = projectArray[s];
					projectArray[s] = tempProject;
				}
			}
		}
		return Arrays.asList(projectArray);
	}
}
