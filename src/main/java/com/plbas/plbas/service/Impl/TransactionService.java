package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.service.DTO.TxDTO;

public interface TransactionService {

    Response<TxDTO> addTransaction();

}
