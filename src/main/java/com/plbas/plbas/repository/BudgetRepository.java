package com.plbas.plbas.repository;

import com.plbas.plbas.entity.Budget;
import com.plbas.plbas.entity.Category;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Long> {
    Budget findByCategory(@NotNull Category category);
}
