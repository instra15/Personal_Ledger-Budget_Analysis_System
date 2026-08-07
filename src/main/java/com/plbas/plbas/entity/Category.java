package com.plbas.plbas.entity;

import com.plbas.plbas.enums.CategoryDirection;
import jakarta.persistence.*;
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
    private Long id;

    private String name;

    private CategoryDirection direction;

    private String description;
}
