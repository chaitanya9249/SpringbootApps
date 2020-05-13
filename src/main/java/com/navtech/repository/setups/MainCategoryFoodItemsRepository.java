package com.navtech.repository.setups;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.navtech.model.setups.MainCategoryFoodItems;

public interface MainCategoryFoodItemsRepository extends JpaRepository<MainCategoryFoodItems, Long>
{
	@Query("FROM MainCategoryFoodItems m where m.mainCategoryFoodId != :mcfoodId and m.mainCategoryFoodName = :mcfoodName ")
	MainCategoryFoodItems existedCheckCondition(
			@Param("mcfoodId") long mcfoodId,
			@Param("mcfoodName") String mcfoodName);
}
