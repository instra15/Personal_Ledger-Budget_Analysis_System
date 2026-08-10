package com.plbas.plbas.service.DTO;

import com.plbas.plbas.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO {
    private Category category;//预算针对哪个支出分类

    private String yearMonth;//预算月份，格式"YYYY-MM"，例如"2026-08"

    private BigDecimal budgetAmount;//该分类这个月的预算总额

    private BigDecimal thresholdPercent = new BigDecimal("80.00");//预警阈值百分比，默认80%，表示花费达到预算的80%时就提醒

    private BigDecimal currentSpent = BigDecimal.ZERO;//当前已花费金额，每次新增支出分录时自动累加


}
