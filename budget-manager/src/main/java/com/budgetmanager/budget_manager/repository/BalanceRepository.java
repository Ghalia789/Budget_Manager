package com.budgetmanager.budget_manager.repository;

import com.budgetmanager.budget_manager.model.Balance;
import com.budgetmanager.budget_manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    // Custom query method to find a Balance by associated User
    Optional<Balance> findByUser(User user);

    Balance findByUser_UserId(int userId);
}