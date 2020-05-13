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
import com.navtech.model.setups.MainCategoryFoodItems;
import com.navtech.repository.setups.MainCategoryFoodItemsRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/setups")
public class MainCategoryFoodItemController 
{
	@Autowired
	private MainCategoryFoodItemsRepository mainCategoryFoodItemRepository;

	@PostMapping("/createMainCategoryFoodItems")
	private ResponseEntity<?> createMainCategoryFoodItems(@Valid @RequestBody MainCategoryFoodItems mainCategoryFoodItems)
	{
		try 
		{
			MainCategoryFoodItems mcExistCheck = mainCategoryFoodItemRepository.existedCheckCondition(mainCategoryFoodItems.getMainCategoryFoodId(), mainCategoryFoodItems.getMainCategoryFoodName());

			if(mcExistCheck  != null)
			{
				return ResponseEntity.badRequest().body("Record was already existed with this name "+mainCategoryFoodItems.getMainCategoryFoodName());	
			}

			mainCategoryFoodItemRepository.save(mainCategoryFoodItems);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok(mainCategoryFoodItems);
	}

	@GetMapping("/listOfMainCategoryFoodItems")
	private ResponseEntity<?> listOfMainCategoryFoodItems()
	{
		List<MainCategoryFoodItems> mainCategoryFoodItemsList = null;

		try 
		{
			mainCategoryFoodItemsList = mainCategoryFoodItemRepository.findAll();			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return ResponseEntity.ok(mainCategoryFoodItemsList);
	}

	@GetMapping("/getMainCategoryFoodItem/{mainCategoryFoodId}")
	private ResponseEntity<?> getMainCategoryFoodItem(@PathVariable("mainCategoryFoodId") long mainCategoryFoodId)
	{
		Optional<MainCategoryFoodItems> mainCategoryFoodItem = null;
		try 
		{
			mainCategoryFoodItem = mainCategoryFoodItemRepository.findById(mainCategoryFoodId);
			if(mainCategoryFoodItem.isPresent())
			{
				return ResponseEntity.ok(mainCategoryFoodItem);				
			}
			else
			{
				return ResponseEntity.ok("No record found with this id ::: "+mainCategoryFoodId);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return ResponseEntity.ok(mainCategoryFoodItem);
	}

	@PutMapping("/updateMainCategoryFoodItem")
	private ResponseEntity<?> updateMainCategoryFoodItem(@Valid @RequestBody MainCategoryFoodItems mainCategoryFoodItems)
	{
		try 
		{
			MainCategoryFoodItems mcExistCheck = mainCategoryFoodItemRepository.existedCheckCondition(mainCategoryFoodItems.getMainCategoryFoodId(), mainCategoryFoodItems.getMainCategoryFoodName());

			if(mcExistCheck  != null)
			{
				return ResponseEntity.badRequest().body("Record was already existed with this name "+mainCategoryFoodItems.getMainCategoryFoodName());	
			}
			else			
			{
				if(mainCategoryFoodItems.getMainCategoryFoodId() > 0)
				{
					mainCategoryFoodItemRepository.save(mainCategoryFoodItems);
					return ResponseEntity.ok(mainCategoryFoodItems);
				}
				else
				{
					return ResponseEntity.badRequest().body("Subcategory food item update item failed");					
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok(mainCategoryFoodItems);
	}
}
