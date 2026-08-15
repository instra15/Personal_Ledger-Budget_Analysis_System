package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.entity.*;
import com.plbas.plbas.exception.BusinessException;
import com.plbas.plbas.repository.*;
import com.plbas.plbas.service.DTO.TransactionDTO;
import com.plbas.plbas.service.DTO.TxDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.*;

import static com.plbas.plbas.enums.CategoryDirection.EXPENSE;
import static com.plbas.plbas.enums.EntryDirection.CREDIT;
import static com.plbas.plbas.enums.EntryDirection.DEBIT;

/**
 * 复式记账事务编排
 */
@Service
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
    public Response<TransactionDTO> addTransaction(TxDTO txDTO)
    {
        //保存一笔交易信息
        Transaction transaction=new Transaction();
        String uuid=UUID.randomUUID().toString();
        transaction.setDate(txDTO.getDate());
        transaction.setRemark(txDTO.getRemark());
        transaction.setTxNo(uuid);
        transaction = transactionRepository.save(transaction);

        List<Entry> entries=new ArrayList<>();
        for (TxDTO.EntryItem entry : txDTO.getEntries())
        {
            Account account=accountRepository.findById(entry.getAccount_id()).orElseThrow(()->new BusinessException("Account does not exist."));
            Category category=categoryRepository.findById(entry.getCategory_id()).orElseThrow(()->new BusinessException("Category does not exist."));

            Entry en=new Entry();
            en.setAccount(account);
            en.setTransaction(transaction);
            en.setCategory(category);
            en.setAmount(entry.getAmount());
            en.setDirection(entry.getDirection());
            entries.add(en);
        }

        //保存分录目录，做借贷平衡检测
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

        //更新预算表和预算表
        String warning=null;
        Set<Account> accounts=new HashSet<>();
        for (Entry entry : entries)
        {
            Account account= entry.getAccount();
            if(entry.getDirection()==DEBIT)
            {
                account.setBalance(account.getBalance().add(entry.getAmount()));
                if (entry.getCategory().getDirection()==EXPENSE)
                {
                    Budget budget=budgetRepository.findByCategoryAndYearMonth(entry.getCategory(), YearMonth.now().toString());
                    if (budget!=null)
                    {
                        budget.setCurrentSpent(budget.getCurrentSpent().add(entry.getAmount()));

                        BigDecimal rate = budget.getCurrentSpent().divide(budget.getBudgetAmount(), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
                        if (rate.compareTo(budget.getThresholdPercent()) >= 0) {
                            warning = "Current spent has reached " + rate + "%";
                        }
                        budgetRepository.save(budget);
                    }
                }
            }
            else
            {
                account.setBalance(account.getBalance().subtract(entry.getAmount()));
            }
            accounts.add(account);
        }
        accountRepository.saveAll(accounts);


        TransactionDTO transactionDTO=new TransactionDTO();
        transactionDTO.setDate(txDTO.getDate());
        transactionDTO.setRemark(txDTO.getRemark());
        transactionDTO.setTx_no(uuid);
        transactionDTO.setWarning(warning);
        return Response.success(transactionDTO);
    }

    public Response<Page<TransactionDTO>> getAllTransactions(Pageable pageable)
    {
        Page<Transaction> page=transactionRepository.findAll(pageable);
        return Response.success(page.map(TransactionDTO::converter));
    }

    public Response<TransactionDTO> getTransactionByTx_no(String tx_no)
    {
        Transaction transaction=transactionRepository.findByTxNo(tx_no);
        if (transaction==null)
        {
            throw new BusinessException("Can not find by tx_no: "+tx_no);
        }
        return Response.success(TransactionDTO.converter(transaction));
    }
}
