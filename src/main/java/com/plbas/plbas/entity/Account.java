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
    private Long id;

    private String name;

    private AccountType type;

    private BigDecimal balance=BigDecimal.ZERO;


}
