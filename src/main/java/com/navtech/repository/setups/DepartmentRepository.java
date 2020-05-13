package com.navtech.repository.setups;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.navtech.model.setups.Departments;

public interface DepartmentRepository extends JpaRepository<Departments, Long> 
{
	@Query("FROM Departments where departmentName = :departmentName")
	Departments findByDepartmentName(@Param("departmentName") String departmentName);
	
	@Query("FROM Departments d where d.departmentId != :deptId and d.departmentName = :deptName ")
	Departments exsitDepartmentCheck(@Param("deptId") long deptId, @Param("deptName") String deptName);
}
