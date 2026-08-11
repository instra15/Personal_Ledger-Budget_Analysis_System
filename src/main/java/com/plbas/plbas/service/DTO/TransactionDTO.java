package com.plbas.plbas.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private String tx_no;//交易流水号，UUID生成，全局唯一，用于标识一笔完整的交易

    private Date date;//交易发生的日期

    private String remark;//备注，例如"中午和同事吃饭"

    private String warning;
}
