package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.entity.Budget;
import com.plbas.plbas.exception.BusinessException;
import com.plbas.plbas.repository.BudgetRepository;
import com.plbas.plbas.service.DTO.BudgetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 预算预警逻辑
 */
@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public Response<Void> createBudget(BudgetDTO budgetDTO)
    {
        Budget budget=budgetRepository.findByCategoryAndYearMonth(budgetDTO.getCategory(),budgetDTO.getYearMonth());
        if (budget!=null)
        {
            throw new BusinessException("Budget exists.");
        }
        budgetRepository.save(BudgetDTO.convert(budgetDTO));
        return Response.success(null);
    }

    public Response<BudgetDTO> getBudgetById(Long id)
    {
        Budget budget=budgetRepository.findById(id).orElseThrow(()->new BusinessException("Budget does not exist."));
        return Response.success(BudgetDTO.convert(budget));
    }

    public Response<Page<BudgetDTO>> getAllBudget(Pageable pageable)
    {
        Page<Budget> page=budgetRepository.findAll(pageable);
        return Response.success(page.map(BudgetDTO::convert));
    }

    public Response<Void> DeleteBudgetById(Long id)
    {
        Budget budget=budgetRepository.findById(id).orElseThrow(()->new BusinessException("Budget does not exist."));
        budgetRepository.deleteById(id);
        return Response.success(null);
    }
}
