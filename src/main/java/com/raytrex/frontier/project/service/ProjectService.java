package com.raytrex.frontier.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raytrex.frontier.repository.CustomerRepository;
import com.raytrex.frontier.repository.EmployeeRepository;
import com.raytrex.frontier.repository.ProjectRepository;
import com.raytrex.frontier.repository.bean.Customer;
import com.raytrex.frontier.repository.bean.Employee;
import com.raytrex.frontier.repository.bean.Project;
import com.raytrex.frontier.repository.bean.ProjectOwner;
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
			Project project = new Project();
			project.setProjectNo(order.getProjectNumber());
			
			String orderCustomer = order.getCustomer();
			String orderCustomerLocal = order.getLocation();
			String targetCustomerId = "";
			for(Customer customer : customerList){
				if(orderCustomer.indexOf(customer.getName()) != -1){
					//因為RPV2的預設值好像都是空白字串所以特別拿出來處理
					if(orderCustomerLocal == null || orderCustomerLocal.isEmpty()){
						if(customer.getRegion() == null){
							targetCustomerId = customer.getCustomerId();
							break;
						}
					}else{
						if(customer.getRegion().equals(orderCustomerLocal)){
							targetCustomerId = customer.getCustomerId();
							break;
						}
					}
				}
			}
			project.setCustomerId(targetCustomerId);
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
			
			projectRespository.save(project);
		}
		return projectRespository.findAll();
	}
	
	public List<Project> getProjectByUid(String uid){
		return projectRespository.findProjectByProjectOwnerUid(uid);
	}
}
