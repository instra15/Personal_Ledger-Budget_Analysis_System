package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.service.DTO.AccountDTO;

import java.util.List;

/**
 * 账户服务接口
 */

public interface AccountService {

    Response<Void> createAccount(AccountDTO accountDTO);

    Response<List<AccountDTO>> getAllAccounts();

    Response<AccountDTO> getAccountById(Long id);

    Response<Void> deleteAccountById(Long id);




}
