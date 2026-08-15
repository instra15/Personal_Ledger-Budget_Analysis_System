package com.plbas.plbas.service.Impl;


import com.plbas.plbas.Response;
import com.plbas.plbas.exception.BusinessException;
import com.plbas.plbas.repository.AccountRepository;
import com.plbas.plbas.service.DTO.AccountDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Response<Void> createAccount(AccountDTO accountDTO)
    {
        if (accountRepository.existsByNameAndType(accountDTO.getName(),accountDTO.getType()))
        {
            throw new BusinessException("Account exists.");
        }
        accountRepository.save(AccountDTO.converter(accountDTO));
        return Response.success(null);
    }

    public Response<List<AccountDTO>> getAllAccounts()
    {
        return Response.success(accountRepository.findAll().stream().map(AccountDTO::converter).toList());
    }

    @Transactional
    public Response<Void> deleteAccount(Long id)
    {
        if (!accountRepository.existsById(id))
        {
            throw new BusinessException("Account does not exist.");
        }
        accountRepository.deleteById(id);
        return Response.success(null);
    }
}
