package com.plbas.plbas.controller;

import com.plbas.plbas.Response;
import com.plbas.plbas.service.DTO.BudgetDTO;
import com.plbas.plbas.service.Impl.BudgetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * 预算设定与预警接口
 */
@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping("/add")
    public Response<Void> addBudget(@RequestBody @Valid BudgetDTO budgetDTO)
    {
        return budgetService.createBudget(budgetDTO);
    }

    @GetMapping("/get/{id}")
    public Response<BudgetDTO> getBudgetById(@PathVariable @NotNull Long id)
    {
        return budgetService.getBudgetById(id);
    }

    @GetMapping("/get")
    public Response<Page<BudgetDTO>> getAllBudget(@PageableDefault Pageable pageable)
    {
        return budgetService.getAllBudget(pageable);
    }

    @DeleteMapping("/delete/{id}")
    public Response<Void> deleteBudgetById(@PathVariable @NotNull Long id)
    {
        return budgetService.DeleteBudgetById(id);
    }

}
