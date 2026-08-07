package com.plbas.plbas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * -- 3. 交易主表 (transaction)
 * -- 说明：记录交易基本信息，一笔交易对应多条分录
 */
@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tx_no;

    private Date date;

    private String remark;
}
