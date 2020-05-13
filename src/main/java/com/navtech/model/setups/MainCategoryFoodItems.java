package com.navtech.model.setups;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "main_category_food_items", uniqueConstraints = { @UniqueConstraint(columnNames = { "mainCategoryFoodName" }) })
public class MainCategoryFoodItems 
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long mainCategoryFoodId;
	
	private String mainCategoryFoodName;
	
	private String mainCategoryFoodStatus;
	
	public MainCategoryFoodItems() 
	{
		
	}

	public long getMainCategoryFoodId() {
		return mainCategoryFoodId;
	}

	public void setMainCategoryFoodId(long mainCategoryFoodId) {
		this.mainCategoryFoodId = mainCategoryFoodId;
	}

	public String getMainCategoryFoodName() 
	{
		return mainCategoryFoodName;
	}

	public void setMainCategoryFoodName(String mainCategoryFoodName) 
	{
		this.mainCategoryFoodName = mainCategoryFoodName;
	}

	public String getMainCategoryFoodStatus() 
	{
		return mainCategoryFoodStatus;
	}

	public void setMainCategoryFoodStatus(String mainCategoryFoodStatus) 
	{
		this.mainCategoryFoodStatus = mainCategoryFoodStatus;
	}
}
