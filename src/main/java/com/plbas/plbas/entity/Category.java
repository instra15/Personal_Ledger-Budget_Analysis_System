package com.plbas.plbas.entity;

import com.plbas.plbas.enums.CategoryDirection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * -- 2. 收支分类表 (category)
 * -- 说明：存储支出或收入的分类标签
 */
@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;//分类名称，例如"餐饮"、"交通"、"工资"

    @NotBlank
    @Column(name = "direction")
    @Enumerated(EnumType.STRING)
    private CategoryDirection direction;//收支方向：INCOME（收入）或 EXPENSE（支出）

    @Column(name = "description")
    private String description;//可选描述，例如"包括三餐和零食"
}
