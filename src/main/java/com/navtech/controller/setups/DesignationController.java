package com.navtech.controller.setups;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.navtech.model.setups.Designation;
import com.navtech.repository.setups.DepartmentRepository;
import com.navtech.repository.setups.DesignationRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/setups")
public class DesignationController 
{
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private DesignationRepository designationRepository;


	@PostMapping("/createDesignation")
	public ResponseEntity<?> createDesignation(@Valid @RequestBody Designation designation)
	{
		try 
		{
			System.out.println("Department  Id from designations "+designation.getDepartmentId());
			System.out.println("Designation Id "+designation.getDesignationId());
			System.out.println("Designation Name "+designation.getDesignationName());
			
//			Designation existDesignationCheck = designationRepository.existDesignationCheck(
//					designation.getDepartments().getDepartmentId(), 
//					designation.getDesignationId(), 
//					designation.getDesignationName());
//			if(existDesignationCheck != null)
//			{
//				return ResponseEntity.ok("Designation is already existed in selected department");				
//			}
//			else
			{
				/*Departments departments = departmentRepository.findByDepartmentName(designation.getDepartmentName());*/ 
				Departments departments = departmentRepository.findById(designation.getDepartmentId()).orElseThrow(() ->new ResourceNotFoundException("Department Not Found through this id "+designation.getDepartments().getDepartmentId()));			
		    	designation.setDepartments(departments);
				designationRepository.save(designation);
				return ResponseEntity.ok(designation);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok(designation);
	}

	@GetMapping("/designationsList")
	public ResponseEntity<?> listOfDesignations()
	{
		List<Designation> designationList = new ArrayList<Designation>();


		List<Designation> desgListPreparation = designationRepository.findAll();
		
		if(desgListPreparation.size() > 0)
		{
			for (int i = 0; i < desgListPreparation.size(); i++) 
			{
				Designation designation = new Designation();
				designation.setDesignationId(desgListPreparation.get(i).getDesignationId());
				designation.setDesignationName(desgListPreparation.get(i).getDesignationName());
				designation.setDesignationStatus(desgListPreparation.get(i).getDesignationStatus());
				designation.setDepartmentName(desgListPreparation.get(i).getDepartments().getDepartmentName());
				designationList.add(designation);
			}
			return ResponseEntity.ok(designationList);
		}
		
		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	 @GetMapping("/editDesignations/{designationId}")
	  public ResponseEntity<?> getTutorialById(@PathVariable("designationId") long designationId) 
	 {
		 Designation designations = null;
	    try 
	    {
	    	designations = designationRepository.findById(designationId).orElseThrow(() -> 
	    	new ResourceNotFoundException("Department Not Found through this id "+designationId));

			if (designations.getDesignationId() > 0) 
			{
				System.out.println("DesignationController.getTutorialById(Department Name) ::: "+designations.getDepartments().getDepartmentName());
				System.out.println("DesignationController.getTutorialById(Department ID) ::: "+designations.getDepartments().getDepartmentId());
				designations.setDepartmentName(designations.getDepartments().getDepartmentName());
				designations.setDepartmentId(designations.getDepartments().getDepartmentId());
				return ResponseEntity.ok(designations);
			} 
			else 
			{
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
	    catch (Exception e) 
	    {
			e.printStackTrace();
		}
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  }

	@PutMapping("/updateDesignation")
	public ResponseEntity<Designation> updateDesignation(@Valid @RequestBody Designation designation)
	{
		try
		{
			if(designation != null)
			{
				Designation existDesignation = designationRepository.findById(designation.getDesignationId()).orElseThrow(() -> 
		    	new ResourceNotFoundException("Department Not Found through this id "+designation.getDesignationId()));
								
				if(existDesignation != null && existDesignation.getDesignationId() != 0)
				{
					/****Updating department details in designation table through department name from response *****/
					Departments departments = departmentRepository.findById(designation.getDepartmentId()).orElseThrow(() ->new ResourceNotFoundException("Department Not Found through this id "+designation.getDepartments().getDepartmentId()));;
					designation.setDepartments(departments);
					designationRepository.save(designation);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok(designation);
	}
}
