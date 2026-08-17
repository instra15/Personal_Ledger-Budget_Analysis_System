package com.plbas.plbas.controller;

import com.plbas.plbas.Response;
import com.plbas.plbas.service.DTO.AccountDTO;
import com.plbas.plbas.service.Impl.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  账户管理接口
 */
@Tag(name = "账户管理")
@RestController
@RequestMapping("/api/account")
@Validated
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Operation(summary = "新增账户")
    @PostMapping("/add")
    public Response<Void> createAccount(@RequestBody @Valid AccountDTO accountDTO)
    {
        return accountService.createAccount(accountDTO);
    }

    @Operation(summary = "查询账户（id）")
    @GetMapping("/get/id/{id}")
    public Response<AccountDTO> getAccountById(@PathVariable @NotNull Long id)
    {
        return accountService.getAccountById(id);
    }

    @Operation(summary = "查询所有账户")
    @GetMapping("/get")
    public Response<List<AccountDTO>> getAllAccount()
    {
        return accountService.getAllAccounts();
    }

    @Operation(summary = "删除账户（id）")
    @GetMapping("/delete/id/{id}")
    public Response<Void> deleteAccountById(@PathVariable @NotNull Long id)
    {
        return accountService.deleteAccountById(id);
    }

}
