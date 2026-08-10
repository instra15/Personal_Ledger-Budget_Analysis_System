package com.plbas.plbas.entity;

import com.plbas.plbas.enums.CategoryDirection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @Column(name = "id")
    private Long id;

    @NotNull
    @JoinColumn(name = "transaction_id")
    @ManyToOne
    private Transaction transaction;//这笔分录属于哪笔交易

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;//这笔分录影响哪个账户

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;//这笔分录属于哪个收支分类

    @NotNull
    @Positive
    @Column(name = "amount")
    private BigDecimal amount;//金额（始终为正数）

    @NotNull
    @Column(name = "direction")
    @Enumerated(EnumType.STRING)
    private CategoryDirection direction;//借贷方向：DEBIT（借方）或 CREDIT（贷方）

}
