package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.entity.Transaction;
import com.plbas.plbas.service.DTO.TransactionDTO;
import com.plbas.plbas.service.DTO.TxDTO;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface TransactionService {

    Response<TransactionDTO> addTransaction(TxDTO txDTO);

    Response<Page<TransactionDTO>> getAllTransactions(Pageable pageable);
}
