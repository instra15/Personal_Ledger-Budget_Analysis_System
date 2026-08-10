package com.plbas.plbas.service.DTO;

import com.plbas.plbas.entity.Account;
import com.plbas.plbas.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    @NotBlank
    private String name;//账户名称，例如"招商银行卡"、"微信钱包"、"现金"

    @NotNull
    private AccountType type;//账户类型：ASSET（资产，如现金、银行卡）、LIABILITY（负债，如信用卡）、EQUITY（权益，如初始本金）

    @NotNull
    private BigDecimal balance=BigDecimal.ZERO;//当前余额，正数表示资产，负数表示负债

    public static AccountDTO converter(Account account)
    {
        AccountDTO accountDTO=new AccountDTO();
        accountDTO.setName(account.getName());
        accountDTO.setType(account.getType());
        accountDTO.setBalance(account.getBalance());
        return accountDTO;
    }

    public static Account converter(AccountDTO accountDTO)
    {
        Account account=new Account();
        account.setName(accountDTO.getName());
        account.setType(accountDTO.getType());
        account.setBalance(accountDTO.getBalance());
        return account;
    }
}
