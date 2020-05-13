package com.navtech.controller.setups;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navtech.exception.ResourceNotFoundException;
import com.navtech.model.setups.Departments;
import com.navtech.repository.setups.DepartmentRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/setups")
public class DepartmentsController 
{
	@Autowired
	private DepartmentRepository departmentsRepository;
	
	@PostMapping("/createDepartment")
    public ResponseEntity<?> createDepartments(@Valid @RequestBody Departments departments) 
	{	
		try 
		{
			Departments existDepartment = departmentsRepository.findByDepartmentName(departments.getDepartmentName());
			
			if(existDepartment != null)
			{
				 return ResponseEntity.ok("Department name is already existed ");
			}
			else
			{
				departmentsRepository.save(departments);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
        return ResponseEntity.ok(departments);
    }
	
	@GetMapping("/departmentsList")
    public ResponseEntity<?> listOfDepartments() 
	{
		List<Departments> listOfDepartments = null;
		try 
		{
			listOfDepartments = departmentsRepository.findAll();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
        return ResponseEntity.ok(listOfDepartments);
    }
	
	@GetMapping("/getDepartments/{departmentId}")
	public ResponseEntity<?> getDepartmentDetailsById(@PathVariable("departmentId") long departmentId)
	{
		Optional<Departments> dept = null;
		try 
		{
			dept = departmentsRepository.findById(departmentId); 
			System.out.println("DepartmentsController.getDepartmentDetailsById() "+dept);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok(dept);
	}
	
	@PutMapping("/updateDepartments")
	public ResponseEntity<?> updateDepartments(@Valid @RequestBody Departments departments)
	{
		try 
		{
			if(departments != null)
			{
				
				Departments alreadyExistDepartment = departmentsRepository.exsitDepartmentCheck(departments.getDepartmentId(), departments.getDepartmentName());
				
				if(alreadyExistDepartment != null)
				{
					return ResponseEntity.ok("Department is already existed with "+departments.getDepartmentName()+" Name");
				}
				else
				{
					long deptId = departments.getDepartmentId();
					Departments existDept = departmentsRepository.findById(deptId).orElseThrow(() -> new ResourceNotFoundException("Department Not Found through this id "+deptId));
					if(existDept != null)
					{
						System.out.println("department name ::: "+departments.getDepartmentName());
						departmentsRepository.save(departments);
						return ResponseEntity.ok(departments);
					}					
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok(departments);
	}
}
