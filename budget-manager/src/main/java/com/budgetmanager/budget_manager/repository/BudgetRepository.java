package com.budgetmanager.budget_manager.repository;

import com.budgetmanager.budget_manager.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    // Custom query to find budgets by user ID
    List<Budget> findAllByUser_UserId(Long userId);

}
