package com.plbas.plbas.entity;

import com.plbas.plbas.enums.CategoryDirection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * -- 4. 分录表 (entry)
 * -- 说明：会计分录表，实现复式记账(一借多贷或一贷多借)
 */
@Entity
@Table(name = "entry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Transaction transaction;

    private Account account;

    private Category category;

    private BigDecimal amount;

    private CategoryDirection direction;

}
