package com.navtech.model.setups;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "designation",uniqueConstraints = {@UniqueConstraint(columnNames = {"designationName"})})
public class Designation 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long designationId;
	
	private String designationName;
	
	private String designationStatus;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "departmentId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
   // @JsonIgnore
    private Departments departments;
	
	@Transient
	private String departmentName;
	
	@Transient
	private long departmentId;
	
	public long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(long designationId) {
		this.designationId = designationId;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getDesignationStatus() {
		return designationStatus;
	}

	public void setDesignationStatus(String designationStatus) {
		this.designationStatus = designationStatus;
	}
	
	public Departments getDepartments() {
		return departments;
	}

	public void setDepartments(Departments departments) {
		this.departments = departments;
	}

	public Designation() 
	{
		
	}

	public Designation(long designationId, String designationName, String designationStatus, Departments departments,
			String departmentName,long departmentId) {
		super();
		this.designationId = designationId;
		this.designationName = designationName;
		this.designationStatus = designationStatus;
		this.departments = departments;
		this.departmentName = departmentName;
		this.departmentId = departmentId;
	}
	
	
}
