package com.plbas.plbas.controller;

import com.plbas.plbas.Response;
import com.plbas.plbas.service.DTO.BudgetDTO;
import com.plbas.plbas.service.Impl.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 预算设定与预警接口
 */
@Tag(name = "预算管理")
@RestController
@RequestMapping("/api/budget")
@Validated
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Operation(summary = "新增预算表")
    @PostMapping("/add")
    public Response<Void> addBudget(@RequestBody @Valid BudgetDTO budgetDTO)
    {
        return budgetService.createBudget(budgetDTO);
    }

    @Operation(summary = "查找预算表（id）")
    @GetMapping("/get/{id}")
    public Response<BudgetDTO> getBudgetById(@PathVariable @NotNull Long id)
    {
        return budgetService.getBudgetById(id);
    }

    @Operation(summary = "获取所有预算表")
    @GetMapping("/get")
    public Response<Page<BudgetDTO>> getAllBudget(@PageableDefault Pageable pageable)
    {
        return budgetService.getAllBudget(pageable);
    }

    @Operation(summary = "删除预算表（id）")
    @DeleteMapping("/delete/{id}")
    public Response<Void> deleteBudgetById(@PathVariable @NotNull Long id)
    {
        return budgetService.DeleteBudgetById(id);
    }

}
