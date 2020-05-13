package com.navtech.controller.setups;

import java.util.ArrayList;
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
import com.navtech.model.setups.SubCategoryFoodItems;
import com.navtech.repository.setups.MainCategoryFoodItemsRepository;
import com.navtech.repository.setups.SubCategoryFoodItemRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/setups")
public class SubCategoryFoodItemController 
{
	@Autowired
	private MainCategoryFoodItemsRepository mainCategoryFoodItemRepository;

	@Autowired
	private SubCategoryFoodItemRepository subCategoryFoodItemsRepository;

	@PostMapping("/createSubCategoryFoodItems")
	private ResponseEntity<?> createSubCategoryFoodItems(@Valid @RequestBody SubCategoryFoodItems subCategoryFoodItems)
	{
		try 
		{
			MainCategoryFoodItems mainCategoryFoodItems = mainCategoryFoodItemRepository.findById(subCategoryFoodItems.getMainCategoryFoodId()).orElseThrow(() ->new ResourceNotFoundException("Main Category Food Item Not Found through this id "+subCategoryFoodItems.getMainCategoryFoodId()));
			subCategoryFoodItems.setMainCategoryItems(mainCategoryFoodItems);

			subCategoryFoodItemsRepository.save(subCategoryFoodItems);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok(subCategoryFoodItems);
	}

	@GetMapping("/listOfSubCategoryFoodItems")
	private ResponseEntity<?> listOfSubCategoryFoodItems()
	{
		List<SubCategoryFoodItems> subCategoryFoodItemsList = null;
		List<SubCategoryFoodItems> subCategoryFoodItemsListPreparation = null;

		try 
		{
			subCategoryFoodItemsList = subCategoryFoodItemsRepository.findAll();
			subCategoryFoodItemsListPreparation = new ArrayList<SubCategoryFoodItems>();
			for (int i = 0; i < subCategoryFoodItemsList.size(); i++) 
			{
				SubCategoryFoodItems subCategoryFoodItems = new SubCategoryFoodItems();

				subCategoryFoodItems.setMainCategoryFoodId(subCategoryFoodItemsList.get(i).getMainCategoryItems().getMainCategoryFoodId());

				subCategoryFoodItems.setMainCategoryItems(subCategoryFoodItemsList.get(i).getMainCategoryItems());

				subCategoryFoodItems.setSubCategoryFoodId(subCategoryFoodItemsList.get(i).getSubCategoryFoodId());
				subCategoryFoodItems.setSubcategoryFoodName(subCategoryFoodItemsList.get(i).getSubcategoryFoodName());
				subCategoryFoodItems.setSubCategoryFoodStatus(subCategoryFoodItemsList.get(i).getSubCategoryFoodStatus());
				subCategoryFoodItemsListPreparation.add(subCategoryFoodItems);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return ResponseEntity.ok(subCategoryFoodItemsListPreparation);
	}
	//
	@GetMapping("/getSubCategoryFoodItem/{subCategoryFoodId}")
	private ResponseEntity<?> getSubCategoryFoodItem(@PathVariable("subCategoryFoodId") long subCategoryFoodId)
	{
		SubCategoryFoodItems subcategoryFoodItem = null;
		MainCategoryFoodItems mainCategoryFoodItem = null;
		try 
		{			
			subcategoryFoodItem = subCategoryFoodItemsRepository.findById(subCategoryFoodId).
					orElseThrow(() -> new ResourceNotFoundException
							("Sub Category Food Item Not Found through this id "+subCategoryFoodId));
			
			System.out.println("SubCategoryFoodItemController.updateSubCategoryFoodItem(While Edit) Main Category Id ::: "
			+subcategoryFoodItem.getMainCategoryItems().getMainCategoryFoodId());

			mainCategoryFoodItem = mainCategoryFoodItemRepository.findById
					(subcategoryFoodItem.getMainCategoryItems().getMainCategoryFoodId()).
					orElseThrow(() ->new ResourceNotFoundException
							("Main Category Food Item Not Found through this id "+subCategoryFoodId));

			subcategoryFoodItem.setMainCategoryFoodId(subcategoryFoodItem.getMainCategoryItems().getMainCategoryFoodId());

			subcategoryFoodItem.setMainCategoryItems(mainCategoryFoodItem);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return ResponseEntity.ok(subcategoryFoodItem);
	}

	@PutMapping("/updateSubCategoryFoodItem")
	private ResponseEntity<?> updateSubCategoryFoodItem(@Valid @RequestBody SubCategoryFoodItems subCategoryFoodItems)
	{
		try 
		{
			if(subCategoryFoodItems.getSubCategoryFoodId() > 0 && subCategoryFoodItemsRepository.findById(subCategoryFoodItems.getSubCategoryFoodId()) != null)
			{
				
				System.out.println("SubCategoryFoodItemController.updateSubCategoryFoodItem(While Update) Main Category Id ::: "+subCategoryFoodItems.getMainCategoryItems().getMainCategoryFoodId());
				
				MainCategoryFoodItems mainCategoryFoodItems = mainCategoryFoodItemRepository.findById(subCategoryFoodItems.getMainCategoryItems().getMainCategoryFoodId()).orElseThrow(() 
						->new ResourceNotFoundException("Main Category Food Item Not Found through this id "
				+subCategoryFoodItems.getMainCategoryFoodId()));
				
				subCategoryFoodItems.setMainCategoryItems(mainCategoryFoodItems);
				
				subCategoryFoodItems.setMainCategoryFoodId(mainCategoryFoodItems.getMainCategoryFoodId());
				
				subCategoryFoodItemsRepository.save(subCategoryFoodItems);
				return ResponseEntity.ok(subCategoryFoodItems);
			}
			else
			{
				return ResponseEntity.badRequest().body("Subcategory food details update failed");					
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok(subCategoryFoodItems);
	}
}
