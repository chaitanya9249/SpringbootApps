package com.navtech.repository.management.employprofile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.navtech.model.management.EmployeeProfile;

@Repository
public interface EmployRepository extends JpaRepository<EmployeeProfile, Long>
{
	/**Find employ details by login id**/
	@Query("FROM EmployeeProfile emp where emp.employId = :employId")
	EmployeeProfile findEmployByEmployId(@Param("employId") long employId);
	
	@Query("FROM EmployeeProfile emp, User u where emp.employId = :employId and emp.employId = u.id")
	List<EmployeeProfile> listOfEmployeeDetails(@Param("employId") long employId);
}
