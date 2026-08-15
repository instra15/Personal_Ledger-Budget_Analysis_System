package com.plbas.plbas.controller;

import com.plbas.plbas.Response;
import com.plbas.plbas.service.DTO.TransactionDTO;
import com.plbas.plbas.service.DTO.TxDTO;
import com.plbas.plbas.service.Impl.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 复式记账交易接口
 */
@Tag(name = "事务管理")
@RestController
@RequestMapping("/api/transaction")
@Validated
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "新增事务")
    @PostMapping("/add")
    public Response<TransactionDTO> addTransaction(@RequestBody @Valid TxDTO txDTO)
    {
        return transactionService.addTransaction(txDTO);
    }

    @Operation(summary = "获取所有事务")
    @GetMapping("/get")
    public Response<Page<TransactionDTO>> getAllTransactions(@PageableDefault Pageable pageable)
    {
        return transactionService.getAllTransactions(pageable);
    }

    @Operation(summary = "查找事务（流水号）")
    @GetMapping("/get/{txNo}")
    public Response<TransactionDTO> getTransactionByTxNo(@PathVariable @NotBlank String txNo)
    {
        return transactionService.getTransactionByTx_no(txNo);
    }

}
