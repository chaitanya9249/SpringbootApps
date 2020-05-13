package com.navtech.model.setups;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "subcategory_food_items", uniqueConstraints = { @UniqueConstraint(columnNames = { "subcategoryFoodName" }) })
public class SubCategoryFoodItems 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long subCategoryFoodId;
	
	private String subcategoryFoodName;
	
	private String subCategoryFoodStatus;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mainCategoryFoodId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
   // @JsonIgnore
	private MainCategoryFoodItems mainCategoryItems;
	
	@Transient
	private long mainCategoryFoodId;
	
	@Transient
	private String mainCategoryFoodName;

	
	
	public long getMainCategoryFoodId() {
		return mainCategoryFoodId;
	}

	public void setMainCategoryFoodId(long mainCategoryFoodId) {
		this.mainCategoryFoodId = mainCategoryFoodId;
	}

	public String getMainCategoryFoodName() {
		return mainCategoryFoodName;
	}

	public void setMainCategoryFoodName(String mainCategoryFoodName) {
		this.mainCategoryFoodName = mainCategoryFoodName;
	}

	public long getSubCategoryFoodId() {
		return subCategoryFoodId;
	}

	public void setSubCategoryFoodId(long subCategoryFoodId) {
		this.subCategoryFoodId = subCategoryFoodId;
	}

	public String getSubcategoryFoodName() {
		return subcategoryFoodName;
	}

	public void setSubcategoryFoodName(String subcategoryFoodName) {
		this.subcategoryFoodName = subcategoryFoodName;
	}

	public String getSubCategoryFoodStatus() {
		return subCategoryFoodStatus;
	}

	public void setSubCategoryFoodStatus(String subCategoryFoodStatus) {
		this.subCategoryFoodStatus = subCategoryFoodStatus;
	}

	public MainCategoryFoodItems getMainCategoryItems() {
		return mainCategoryItems;
	}

	public void setMainCategoryItems(MainCategoryFoodItems mainCategoryItems) {
		this.mainCategoryItems = mainCategoryItems;
	}
	
	public SubCategoryFoodItems() 
	{
		
	}
}
