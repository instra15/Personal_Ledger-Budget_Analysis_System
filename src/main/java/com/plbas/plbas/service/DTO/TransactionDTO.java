package com.plbas.plbas.service.DTO;

import com.plbas.plbas.entity.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    @NotBlank
    private String tx_no;//交易流水号，UUID生成，全局唯一，用于标识一笔完整的交易

    @NotNull
    private Date date;//交易发生的日期

    private String remark;//备注，例如"中午和同事吃饭"

    private String warning;

    public static Transaction converter(TransactionDTO transactionDTO)
    {
        Transaction transaction=new Transaction();
        transaction.setTx_no(transactionDTO.getTx_no());
        transaction.setRemark(transactionDTO.getRemark());
        transaction.setDate(transactionDTO.getDate());
        return transaction;
    }

    public static TransactionDTO converter(Transaction transaction)
    {
        TransactionDTO transactionDTO=new TransactionDTO();
        transactionDTO.setTx_no(transaction.getTx_no());
        transactionDTO.setRemark(transaction.getRemark());
        transactionDTO.setDate(transaction.getDate());
        return transactionDTO;
    }
}
