package com.navtech.repository.setups;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.navtech.model.setups.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Long> 
{
//	@Query("FROM Designation d where d.designationId != :desgId "
//			+ "and d.departmentId != :deptId and d.departmentName = :desgName")
//	Designation existDesignationCheck(
//			@Param("desgId") long desgId,
//			@Param("deptId") long deptId,
//			@Param("desgName") String desgName);
}
