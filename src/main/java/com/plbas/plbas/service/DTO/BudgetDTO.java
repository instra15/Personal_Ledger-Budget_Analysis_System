package com.plbas.plbas.service.DTO;

import com.plbas.plbas.entity.Budget;
import com.plbas.plbas.entity.Category;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO {
    @NotNull
    private Category category;//预算针对哪个支出分类

    @NotBlank
    private String yearMonth;//预算月份，格式"YYYY-MM"，例如"2026-08"

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal budgetAmount;//该分类这个月的预算总额

    @NotNull
    @DecimalMin("0.00")
    @DecimalMax("100.00")
    private BigDecimal thresholdPercent = new BigDecimal("80.00");//预警阈值百分比，默认80%，表示花费达到预算的80%时就提醒

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal currentSpent = BigDecimal.ZERO;//当前已花费金额，每次新增支出分录时自动累加

    public static BudgetDTO convert(Budget budget)
    {
        BudgetDTO budgetDTO=new BudgetDTO();
        budgetDTO.setCategory(budget.getCategory());
        budgetDTO.setYearMonth(budget.getYearMonth());
        budgetDTO.setBudgetAmount(budget.getBudgetAmount());
        budgetDTO.setThresholdPercent(budget.getThresholdPercent());
        budgetDTO.setCurrentSpent(budget.getCurrentSpent());
        return budgetDTO;
    }

    public static Budget convert(BudgetDTO budgetDTO)
    {
        Budget budget=new Budget();
        budget.setCategory(budgetDTO.getCategory());
        budget.setYearMonth(budgetDTO.getYearMonth());
        budget.setBudgetAmount(budgetDTO.getBudgetAmount());
        budget.setThresholdPercent(budgetDTO.getThresholdPercent());
        budget.setCurrentSpent(budgetDTO.getCurrentSpent());
        return budget;
    }

}
