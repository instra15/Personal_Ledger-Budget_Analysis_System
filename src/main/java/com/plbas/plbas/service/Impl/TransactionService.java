package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.service.DTO.TransactionDTO;
import com.plbas.plbas.service.DTO.TxDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    Response<TransactionDTO> addTransaction(TxDTO txDTO);

    Response<Page<TransactionDTO>> getAllTransactions(Pageable pageable);

    Response<TransactionDTO> getTransactionByTx_no(String tx_no);
}
