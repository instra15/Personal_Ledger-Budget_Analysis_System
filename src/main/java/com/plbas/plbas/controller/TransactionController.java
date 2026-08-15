package com.plbas.plbas.controller;

import com.plbas.plbas.Response;
import com.plbas.plbas.service.DTO.TransactionDTO;
import com.plbas.plbas.service.DTO.TxDTO;
import com.plbas.plbas.service.Impl.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * 复式记账交易接口
 */
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add")
    public Response<Void> addTransaction(@RequestBody @Valid TxDTO txDTO)
    {
        return transactionService.addTransaction(txDTO);
    }

    @GetMapping("/get")
    public Response<Page<TransactionDTO>> getAllTransactions(@PageableDefault Pageable pageable)
    {
        return transactionService.getAllTransactions(pageable);
    }

    @GetMapping("/get/{txNo}")
    public Object getTransactionByTxNo(@PathVariable @NotBlank String txNo)
    {
        return transactionService.getTransactionByTx_no(txNo);
    }

}
