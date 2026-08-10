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
    @Column(name = "id")
    private Long id;

    @Column(name = "tx_no")
    private String tx_no;//交易流水号，UUID生成，全局唯一，用于标识一笔完整的交易

    @Column(name = "date")
    private Date date;//交易发生的日期

    @Column(name = "remark")
    private String remark;//备注，例如"中午和同事吃饭"
}
