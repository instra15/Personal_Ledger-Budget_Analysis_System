package com.plbas.plbas.controller;

import com.plbas.plbas.Response;
import com.plbas.plbas.entity.Account;
import com.plbas.plbas.service.DTO.AccountDTO;
import com.plbas.plbas.service.Impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  账户管理接口
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/add")
    public Response<Void> createAccount(AccountDTO accountDTO)
    {
        return accountService.createAccount(accountDTO);
    }

    @GetMapping("/get")
    public Response<List<AccountDTO>> getAllAccount()
    {
        return accountService.getAllAccounts();
    }




}
