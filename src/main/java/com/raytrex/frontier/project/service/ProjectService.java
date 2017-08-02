package com.raytrex.frontier.project.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raytrex.frontier.repository.CustomerRepository;
import com.raytrex.frontier.repository.EmployeeRepository;
import com.raytrex.frontier.repository.ProjectOwnerRepository;
import com.raytrex.frontier.repository.ProjectRepository;
import com.raytrex.frontier.repository.ProjectStatusRepository;
import com.raytrex.frontier.repository.SerialNoRepository;
import com.raytrex.frontier.repository.bean.Customer;
import com.raytrex.frontier.repository.bean.Employee;
import com.raytrex.frontier.repository.bean.Project;
import com.raytrex.frontier.repository.bean.ProjectOwner;
import com.raytrex.frontier.repository.bean.ProjectStatus;
import com.raytrex.frontier.repository.bean.SerialNo;
import com.raytrex.frontier.repository.bean.Task;
import com.raytrex.frontier.task.service.TaskService;
import com.raytrex.rpv.repository.OrderListRepository;
import com.raytrex.rpv.repository.bean.OrderList;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRespository;
	@Autowired
	private ProjectStatusRepository projectStatusRepository;
	@Autowired
	private ProjectOwnerRepository projectOwnerRepository;
	@Autowired
	private OrderListRepository orderListRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private TaskService taskService;
	@Autowired
	private SerialNoRepository serialRepository;
	
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
			projectStatus.setStartDate(new Timestamp(order.getRaytrexPoDate().getTime()));
			projectStatus.setDescription(order.getConfig());
			//依照階段來分級Project的Priority
			if(order.getMoveIn() != null){
				if(order.getMoveIn().getTime() > System.currentTimeMillis()){//還沒完成
					projectStatus.setPriority(1);
					projectStatus.setAlarmDate(new Timestamp(order.getMoveIn().getTime()));
				}
			}
			if(order.getInstallHW() != null){
				if(order.getInstallHW().getTime() > System.currentTimeMillis() && projectStatus.getPriority() > 1){//還沒完成
					projectStatus.setPriority(2);
					projectStatus.setAlarmDate(new Timestamp(order.getInstallHW().getTime()));
				}
			}
			if(order.getInstallSW() != null){
				if(order.getInstallSW().getTime() > System.currentTimeMillis() && projectStatus.getPriority() > 2){//還沒完成
					projectStatus.setPriority(3);
					projectStatus.setAlarmDate(new Timestamp(order.getInstallSW().getTime()));
				}
			}
			if(order.getInstallMeasure() != null){
				if(order.getInstallMeasure().getTime() > System.currentTimeMillis() && projectStatus.getPriority() > 3){//還沒完成
					projectStatus.setPriority(4);
					projectStatus.setAlarmDate(new Timestamp(order.getInstallMeasure().getTime()));
				}
			}
			if(order.getUat() != null){
				if(order.getUat().getTime() > System.currentTimeMillis() && projectStatus.getPriority() > 4){//還沒完成
					projectStatus.setPriority(5);
					projectStatus.setAlarmDate(new Timestamp(order.getUat().getTime()));
				}
			}
			if(order.getFat() != null){
				if(order.getFat().getTime() > System.currentTimeMillis() && projectStatus.getPriority() > 5){//還沒完成
					projectStatus.setPriority(3);
					projectStatus.setAlarmDate(new Timestamp(order.getFat().getTime()));
				}
				if(order.getFat().getTime() < System.currentTimeMillis()){//已經超過時間就結束Project
					projectStatus.setEndDate(new Timestamp(order.getFat().getTime()));
				}
				projectStatus.setDueDate(new Timestamp(order.getFat().getTime()));//Project預計結束的時間
				
			}
			
			projectStatus.setStatusUuid(UUID.randomUUID().toString());

			project.getStatusList().add(projectStatus);
			
			projectRespository.save(project);
		}
		return projectRespository.findAll();
	}
	
	public List<Project> getProjectByUid(String uid){
		List<Project> list = projectRespository.findProjectByProjectOwnerUid(uid);
		return list;
	}
	
	public List<Project> getAllProject(){
		return projectRespository.findAll();
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

	public Project save(Project project,List<Task> taskList) {
		//Project status如果有變動過就直接新增一筆
		Project dbProject = projectRespository.findOne(project.getProjectNo());
		ProjectStatus cProjectStatus = project.getStatusList().get(0);
		if(dbProject == null){
			Project newProject = new Project();
			newProject.setProjectNo(project.getProjectNo());
			newProject.setCustomerId(project.getCustomerId());
			newProject.setAttachUuid(project.getAttachUuid());
			newProject.setPermissionId(project.getPermissionId());
			projectRespository.save(newProject);
			cProjectStatus.setProject(project);
			projectStatusRepository.save(cProjectStatus);
		}else{
			ProjectStatus dbProjectStatus = dbProject.getStatusList() != null?dbProject.getStatusList().get(0):new ProjectStatus();
			
			if(!dbProjectStatus.equals(cProjectStatus)){
				cProjectStatus.setProject(project);
				cProjectStatus.setStatusUuid(UUID.randomUUID().toString());
				cProjectStatus.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				projectStatusRepository.save(cProjectStatus);
			}
		}

		//Project Owner如果不存在在裡面了就印上LeaveDate
		for(ProjectOwner projectOwner : project.getOwnerList()){
			boolean notFound = true;
			if(dbProject != null){
				Iterator<ProjectOwner> poIt = dbProject.getOwnerList().iterator();
				while(poIt.hasNext()){
					ProjectOwner dbProjectOwner = poIt.next();
					if(dbProjectOwner.getLeaveDate() != null){
						poIt.remove();
					}else if(projectOwner.getUid().equals(dbProjectOwner.getUid())){
						poIt.remove();
						notFound = false;
						break;
					}
				}
			}
			
			if(notFound){
				projectOwner.setProject(project);
				projectOwner.setProjectNo(project.getProjectNo());
				projectOwner.setJoinDate(new Date());
				projectOwner.setProjectNo(project.getProjectNo());
				projectOwnerRepository.save(projectOwner);
			}
		}
		//開始移除不存在的Owner
		if(dbProject != null){
			for(ProjectOwner dbPo: dbProject.getOwnerList()){
				dbPo.setLeaveDate(new Date());
				projectOwnerRepository.save(dbPo);
			}
		}
		
		Project newProject = projectRespository.save(project);
		//儲存Task List
		for(Task task : taskList){
			taskService.save(task);
		}
		return newProject;
	}
	
	public String getProjectSerialNo(){
		SerialNo serialNo = serialRepository.findOne("RX");
		DecimalFormat df = new DecimalFormat("0000000");
		if(serialNo == null){
			SerialNo sn = new SerialNo();
			sn.setCount(0);
			sn.setSerialName("RX");
			sn.setDescription("Project單序號");
			serialNo = serialRepository.save(sn);
		}
		Integer count = serialNo.getCount()+1;
		serialNo.setCount(count);
		String no = "RX"+df.format(count);
		serialRepository.save(serialNo);
		return no;
	}
}
