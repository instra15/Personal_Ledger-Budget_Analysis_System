package com.plbas.plbas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * -- 5. 月度预算表 (budget)
 * -- 说明：按月设置分类预算
 */
@Entity
@Table(name = "budget", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"category_id", "yearMonth"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;//预算针对哪个支出分类

    @NotBlank
    @Column(name = "year_month")
    private String yearMonth;//预算月份，格式"YYYY-MM"，例如"2026-08"

    @NotNull
    @Positive
    @Column(name = "budget_amount")
    private BigDecimal budgetAmount;//该分类这个月的预算总额

    @NotNull
    @Positive
    @Column(name = "threshold_percent")
    private BigDecimal thresholdPercent = new BigDecimal("80.00");//预警阈值百分比，默认80%，表示花费达到预算的80%时就提醒

    @NotNull
    @Column(name = "current_spent")
    private BigDecimal currentSpent = BigDecimal.ZERO;//当前已花费金额，每次新增支出分录时自动累加

}
