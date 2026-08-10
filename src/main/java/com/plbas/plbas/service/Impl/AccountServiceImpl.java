package com.plbas.plbas.service.Impl;


import com.plbas.plbas.Response;
import com.plbas.plbas.entity.Account;
import com.plbas.plbas.repository.AccountRepository;
import com.plbas.plbas.service.DTO.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Response<AccountDTO> createAccount(AccountDTO accountDTO)
    {
        Account account=accountRepository.save(AccountDTO.converter(accountDTO));
        return Response.success(AccountDTO.converter(account));
    }

    public Response<List<AccountDTO>> getAllAccounts()
    {
        return Response.success(accountRepository.findAll().stream().map(AccountDTO::converter).toList());
    }

}
