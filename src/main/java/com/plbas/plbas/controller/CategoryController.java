package com.plbas.plbas.controller;

import com.plbas.plbas.Response;
import com.plbas.plbas.entity.Category;
import com.plbas.plbas.service.DTO.CategoryDTO;
import com.plbas.plbas.service.Impl.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类标签接口
 */
@Tag(name = "支出分类管理")
@RestController
@RequestMapping("/api/category")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "新增分类")
    @PostMapping("/add")
    public Response<Void> createCategory(@RequestBody @Valid CategoryDTO categoryDTO)
    {
        return categoryService.createCategory(categoryDTO);
    }

    @Operation(summary = "获取所有分类")
    @GetMapping("/get")
    public Response<List<CategoryDTO>> getAllCategory()
    {
        return categoryService.getAllCategory();
    }

}
