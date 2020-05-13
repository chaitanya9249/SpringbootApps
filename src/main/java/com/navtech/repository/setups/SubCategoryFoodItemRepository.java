package com.navtech.repository.setups;

import org.springframework.data.jpa.repository.JpaRepository;

import com.navtech.model.setups.SubCategoryFoodItems;

public interface SubCategoryFoodItemRepository extends JpaRepository<SubCategoryFoodItems,Long>
{
	
}
