package com.raytrex.frontier.employee.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.raytrex.frontier.repository.DepartmentRepository;
import com.raytrex.frontier.repository.EmployeeInfoRepository;
import com.raytrex.frontier.repository.EmployeeRepository;
import com.raytrex.frontier.repository.EmployeeRolesRepository;
import com.raytrex.frontier.repository.PermissionRepository;
import com.raytrex.frontier.repository.RoleRepository;
import com.raytrex.frontier.repository.SerialNoRepository;
import com.raytrex.frontier.repository.bean.Employee;
import com.raytrex.frontier.repository.bean.EmployeeInfo;
import com.raytrex.frontier.repository.bean.EmployeeRoles;
import com.raytrex.frontier.repository.bean.Permission;
import com.raytrex.frontier.repository.bean.SerialNo;
import com.raytrex.microsoft.service.ADALService;

@Service
public class EmployeeService {
	@Autowired
	private ADALService adalService;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeInfoRepository employessInfoRepository;
	@Autowired
	private EmployeeRolesRepository employeeRolesRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private SerialNoRepository serialRepository;
	
	/**
	 * 初始化Azure上所有的帳號,到Frontier的身上
	 * @param access_token
	 * @return 
	 */
	public List<Employee> initEmployeeRepositoryFromAzure(String access_token){
		String userListString = adalService.listUsers(access_token);
		Gson gson = new Gson();
		Map result = gson.fromJson(userListString, Map.class);
		if(result.containsKey("value")){
			Object valueObject= result.get("value");
			if(valueObject instanceof List){
				List<Map> valueList = (List<Map>) valueObject;
				List<Employee> employeesList = new ArrayList<Employee>();
				for(Map value : valueList){
					String uid = value.get("id").toString();
					Employee employees = employeeRepository.findOne(uid);
					if(employees == null){
						employees = new Employee();
						employees.setUid(value.get("id").toString());
					}
					
					employees.setMail(value.get("mail") == null?"":value.get("mail").toString());
					employees.setEmpNo(employees.getUid());
					EmployeeInfo info = employees.getEmployeesInfo();
					if(info == null){
						info = new EmployeeInfo();
					}
					info.setUid(employees.getUid());
					info.setLastName(value.get("displayName") == null?"":value.get("displayName").toString());
					info.setFirstName(value.get("givenName") == null?"":value.get("givenName").toString());
					info.setPreferredLanguage(value.get("preferredLanguage") == null?"zh-TW": value.get("preferredLanguage").toString());
					byte[] image = adalService.getUserProfilePhoto(access_token, employees.getUid(), info.getLastName());
					if(image != null){
						info.setImage(image);
					}
					employees.setEmployeesInfo(info);
					employeesList.add(employees);
				}
				employeeRepository.save(employeesList);
			}
		}
		return employeeRepository.findAll();
	}
	
	public List<Employee> getCompanyUsers(){
		List<Employee> company = employeeRepository.findAll();
		for(Employee e : company){
			Iterator<EmployeeRoles> it = e.getRoleList().iterator();
			while(it.hasNext()){
				EmployeeRoles role = it.next();
				if(role.getToDate() != null){
					it.remove();
				}
			}
		}
		return company;
	}
	
	public Employee saveEmployee(Employee employee){
		return employeeRepository.save(employee);
	}
	public EmployeeInfo saveEmployeeInfo(EmployeeInfo employeeInfo){
		return employessInfoRepository.save(employeeInfo);
	}
	public EmployeeRoles saveEmployeeRoles(EmployeeRoles employeeRoles){
		return employeeRolesRepository.save(employeeRoles);
	}

	public Permission savePermission(Permission permission){
		return permissionRepository.save(permission);
	}
	
	public Employee getEmployee(String uid){
		return employeeRepository.findOne(uid);
	}
	
	public List<Permission> getPermissionByUid(String uid){
		return permissionRepository.findByUid(uid);
	}
	public List<Permission> getPermissionByRoleId(String roleId){
		return permissionRepository.findByRoleId(roleId);
	}
	
	public String getNewEmployeeNo(){
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
		DecimalFormat df = new DecimalFormat("000");
		SerialNo serialNo = serialRepository.findOne("RTX");
		if(serialNo == null){
			SerialNo sn = new SerialNo();
			sn.setCount(0);
			sn.setSerialName("RTX");
			sn.setDescription("員工編號");
			serialNo = serialRepository.save(sn);
		}
		//先來找看看該EMP NO是否被使用,沒有就不用加1
		Integer count = serialNo.getCount();
		String no = "";
		
		while(true){
			no = "RTX"+sdf.format(new Date())+ df.format(count);
			Employee employee = employeeRepository.findOneByEmpNo(no);
			if(employee != null){//
				count++;
			}else{
				serialNo.setCount(count);
				serialNo = serialRepository.save(serialNo);
				break;
			}
		}
		return no;
	}
}
