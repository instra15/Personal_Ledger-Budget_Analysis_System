package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.entity.*;
import com.plbas.plbas.exception.BusinessException;
import com.plbas.plbas.repository.*;
import com.plbas.plbas.service.DTO.TxDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.plbas.plbas.enums.CategoryDirection.EXPENSE;
import static com.plbas.plbas.enums.EntryDirection.CREDIT;
import static com.plbas.plbas.enums.EntryDirection.DEBIT;

/**
 * 复式记账事务编排
 */
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Transactional
    public Response<TxDTO> addTransaction(TxDTO txDTO)
    {
        Transaction transaction=new Transaction();

        String uuid=UUID.randomUUID().toString();
        transaction.setDate(txDTO.getDate());
        transaction.setRemark(txDTO.getRemark());
        transaction.setTx_no(uuid);
        transaction = transactionRepository.save(transaction);
        Long id=transaction.getId();

        List<Entry> entries=new ArrayList<>();
        for (TxDTO.EntryItem entry : txDTO.getEntries())
        {
            Account account=accountRepository.findById(entry.getAccount_id()).orElseThrow(()->new BusinessException("Account does not exist."));
            Category category=categoryRepository.findById(entry.getAccount_id()).orElseThrow(()->new BusinessException("Category does not exist."));

            Entry en=new Entry();
            en.setAccount(account);
            en.setCategory(category);
            en.setAmount(entry.getAmount());
            en.setDirection(entry.getDirection());
            entries.add(en);
        }

        BigDecimal debitCount=BigDecimal.ZERO;
        BigDecimal creditCount=BigDecimal.ZERO;
        for (Entry entry : entries)
        {
            if (entry.getDirection().equals(DEBIT))
            {
                debitCount=debitCount.add(entry.getAmount());
            }
            if (entry.getDirection().equals(CREDIT))
            {
                creditCount=creditCount.add(entry.getAmount());
            }
        }
        if (debitCount.compareTo(creditCount)!=0)
        {
            throw new BusinessException("Debit count is not equal to credit count.");
        }

        entryRepository.saveAll(entries);

        String warning=null;
        for (Entry entry : entries)
        {
            Account account=accountRepository.findById(entry.getId()).orElseThrow(()->new BusinessException("Account does not exist."));
            if(entry.getDirection()==DEBIT)
            {
                account.setBalance(account.getBalance().add(entry.getAmount()));
                if (entry.getCategory().getDirection()==EXPENSE)
                {
                    Budget budget=budgetRepository.findByCategory(entry.getCategory());
                    budget.setCurrentSpent(budget.getBudgetAmount().add(entry.getAmount()));

                    BigDecimal rate=budget.getCurrentSpent().divide(budget.getBudgetAmount(),4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
                    if (rate.compareTo(budget.getThresholdPercent())>=0)
                    {
                        warning="Current spent has reached " + budget.getThresholdPercent() + "%";
                    }
                }
            }
            else
            {
                account.setBalance(account.getBalance().subtract(entry.getAmount()));
            }
            accountRepository.save(account);
        }


    }

}
