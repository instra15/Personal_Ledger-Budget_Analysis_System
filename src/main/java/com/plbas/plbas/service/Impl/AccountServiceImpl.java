package com.plbas.plbas.service.Impl;


import com.plbas.plbas.Response;
import com.plbas.plbas.repository.AccountRepository;
import com.plbas.plbas.service.DTO.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Response<Void> createAccount(AccountDTO accountDTO)
    {
        accountRepository.save(AccountDTO.converter(accountDTO));
        return Response.success(null);
    }

    public Response<List<AccountDTO>> getAllAccounts()
    {
        return Response.success(accountRepository.findAll().stream().map(AccountDTO::converter).toList());
    }

}
