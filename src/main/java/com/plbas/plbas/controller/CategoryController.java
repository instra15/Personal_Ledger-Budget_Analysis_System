package com.plbas.plbas.controller;

import com.plbas.plbas.Response;
import com.plbas.plbas.entity.Category;
import com.plbas.plbas.service.DTO.CategoryDTO;
import com.plbas.plbas.service.Impl.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类标签接口
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public Response<Void> createCategory(@RequestBody @Valid CategoryDTO categoryDTO)
    {
        return categoryService.createCategory(categoryDTO);
    }

    @GetMapping("/get")
    public Response<List<CategoryDTO>> getAllCategory()
    {
        return categoryService.getAllCategory();
    }

}
