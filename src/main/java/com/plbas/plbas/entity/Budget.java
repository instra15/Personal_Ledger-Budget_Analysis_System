package com.plbas.plbas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * -- 5. 月度预算表 (budget)
 * -- 说明：按月设置分类预算
 */
@Entity
@Table(name = "budget")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    private Long id;

    private Category category;

    private String yearMonth;

    private BigDecimal budgetAmount;

    private BigDecimal thresholdPercent = new BigDecimal("80.00");

    private BigDecimal currentSpent = BigDecimal.ZERO;

}
