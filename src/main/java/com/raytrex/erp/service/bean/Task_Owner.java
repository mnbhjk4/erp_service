package com.raytrex.erp.service.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Task_Owner implements Serializable {
	@Id
	@GeneratedValue
	@Column(name="task_owner_serial_no")
	private Integer taskOwnerSerialNo;
	
	@Column(name="task_no")
	private String taskNo;
	private String uid;
	
	@Column(name="join_date")
	private Date joinDate;
	
	@Column(name="leave_date")
	private Date leaveDate;

	public Integer getTaskOwnerSerialNo() {
		return taskOwnerSerialNo;
	}

	public void setTaskOwnerSerialNo(Integer taskOwnerSerialNo) {
		this.taskOwnerSerialNo = taskOwnerSerialNo;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Task_Owner){
			Field[] fields = this.getClass().getDeclaredFields();
			boolean isSameValue = true;
			for(Field field : fields){
				try {
					String targetValue = field.get(this) == null?"":field.get(this).toString();
					String compareValue = field.get(obj) == null?"":field.get(obj).toString();
					if(!targetValue.equals(compareValue)){
						isSameValue = false;
					}
				} catch (IllegalArgumentException e) {
					isSameValue = false;
				} catch (IllegalAccessException e) {
					isSameValue = false;
				}
			}
			return isSameValue;
		}else{
			return super.equals(obj);
		}
		
	}
	
	
}
