package com.plbas.plbas.controller;

import com.plbas.plbas.Response;
import com.plbas.plbas.entity.Account;
import com.plbas.plbas.service.DTO.AccountDTO;
import com.plbas.plbas.service.Impl.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    public Response<Void> createAccount(@RequestBody @Valid AccountDTO accountDTO)
    {
        return accountService.createAccount(accountDTO);
    }

    @GetMapping("/get")
    public Response<List<AccountDTO>> getAllAccount()
    {
        return accountService.getAllAccounts();
    }




}
