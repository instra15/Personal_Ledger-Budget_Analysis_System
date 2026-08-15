package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.service.DTO.BudgetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BudgetService {

    Response<Void> createBudget(BudgetDTO budgetDTO);

    Response<BudgetDTO> getBudgetById(Long id);

    Response<Page<BudgetDTO>> getAllBudget(Pageable pageable);

    Response<Void> DeleteBudgetById(Long id);

}
