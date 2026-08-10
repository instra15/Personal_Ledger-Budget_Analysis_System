package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.service.DTO.AccountDTO;

import java.util.List;

/**
 * 账户服务接口
 */
public interface AccountService {

    Response<AccountDTO> createAccount(AccountDTO accountDTO);

    Response<List<AccountDTO>> getAllAccounts();






}
