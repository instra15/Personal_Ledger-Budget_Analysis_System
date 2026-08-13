package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.entity.Account;
import com.plbas.plbas.entity.Entry;
import com.plbas.plbas.entity.Transaction;
import com.plbas.plbas.enums.CategoryDirection;
import com.plbas.plbas.enums.EntryDirection;
import com.plbas.plbas.repository.AccountRepository;
import com.plbas.plbas.repository.CategoryRepository;
import com.plbas.plbas.repository.EntryRepository;
import com.plbas.plbas.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.plbas.plbas.enums.CategoryDirection.EXPENSE;

public class ReportServiceImpl implements ReportService{

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     *
     * @param yearMonth  月份
     * @param direction  类型（收/支）
     * @return  月度收支趋势*
     *
     */
    public Response<Map<String,Object>> getMonthTrendByYearMonth(String yearMonth, CategoryDirection direction)
    {
        YearMonth ym = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));

        // 当月第一天 00:00:00（转为 Date）
        LocalDate firstDay = ym.atDay(1);
        Date start = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // 当月最后一天 23:59:59
        LocalDate lastDay = ym.atEndOfMonth();
        Date end = Date.from(lastDay.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

        List<Transaction> transactions=transactionRepository.findAllByDateBetween(start,end);
        List<Entry> entries=entryRepository.findByTransactionIn(transactions);

        BigDecimal total = BigDecimal.ZERO;
        for (Entry entry : entries) 
        {
            boolean matches = false;
            if (direction == CategoryDirection.EXPENSE)
            {
                matches = entry.getDirection() == EntryDirection.DEBIT
                        && entry.getCategory().getDirection() == CategoryDirection.EXPENSE;
            }
            else if (direction == CategoryDirection.INCOME)
            {
                matches = entry.getDirection() == EntryDirection.CREDIT
                        && entry.getCategory().getDirection() == CategoryDirection.INCOME;
            }
            if (matches)
            {
                total = total.add(entry.getAmount());
            }
        }

        Map<String,Object> result=new LinkedHashMap<>();
        result.put("month", yearMonth);
        result.put("type", direction.name());
        result.put("total", total);
        return Response.success(result);
    }

    /**
     *
     * @return 支出占比
     *分类支出占比
     */
    public Response<Map<String,Object>> getCategoryPie()
    {
        List<Entry> entries=entryRepository.findByDirectionAndCategoryDirection(EntryDirection.DEBIT,EXPENSE);
        if (entries.isEmpty())
        {
            return Response.success(new HashMap<>());
        }

        Map<String,BigDecimal> categorySum=new LinkedHashMap<>();
        BigDecimal totalSum=BigDecimal.ZERO;
        for (Entry entry : entries)
        {
            String name=entry.getCategory().getName();
            BigDecimal amount=entry.getAmount();
            categorySum.merge(name,amount,BigDecimal::add);
            totalSum=totalSum.add(amount);
        }

        Map<String,Object> result=new LinkedHashMap<>();
        for (Map.Entry<String, BigDecimal> entry:categorySum.entrySet())
        {
            String name=entry.getKey();
            BigDecimal amount=entry.getValue();
            result.put(name,(amount.divide(totalSum,2,RoundingMode.HALF_UP).multiply(new BigDecimal("100"))+"%"));
        }
        return Response.success(result);
    }

    public Response<BigDecimal> getTotalAccountBalance()
    {
        List<Account> accounts=accountRepository.findAll();
        if (accounts.isEmpty())
        {
            return Response.success(BigDecimal.ZERO);
        }

        BigDecimal total=BigDecimal.ZERO;
        for (Account account : accounts)
        {
            total=total.add(account.getBalance());
        }
        return Response.success(total);
    }



}
