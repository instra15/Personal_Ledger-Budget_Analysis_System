package com.plbas.plbas.service.DTO;

import com.plbas.plbas.entity.Category;
import com.plbas.plbas.enums.CategoryDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private String name;//分类名称，例如"餐饮"、"交通"、"工资"

    private CategoryDirection direction;//收支方向：INCOME（收入）或 EXPENSE（支出）

    private String description;//可选描述，例如"包括三餐和零食"

    public static CategoryDTO converter(Category category)
    {
        CategoryDTO categoryDTO=new CategoryDTO();
        categoryDTO.setName(category.getName());
        categoryDTO.setDirection(category.getDirection());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    public static Category converter(CategoryDTO categoryDTO)
    {
        Category category=new Category();
        category.setName(categoryDTO.getName());
        category.setDirection(categoryDTO.getDirection());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }
}
