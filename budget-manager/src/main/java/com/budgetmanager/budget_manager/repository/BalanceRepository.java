package com.budgetmanager.budget_manager.repository;

import com.budgetmanager.budget_manager.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Balance findByUser_UserId(int userId);
}