package com.plbas.plbas.entity;

import com.plbas.plbas.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * -- 1. 账户表 (account)
 * -- 说明：存储现金、银行卡、信用卡等信息
 */
@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;//账户名称，例如"招商银行卡"、"微信钱包"、"现金"

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccountType type;//账户类型：ASSET（资产，如现金、银行卡）、LIABILITY（负债，如信用卡）、EQUITY（权益，如初始本金）

    @Column(name = "balance")
    private BigDecimal balance=BigDecimal.ZERO;//当前余额，正数表示资产，负数表示负债


}
