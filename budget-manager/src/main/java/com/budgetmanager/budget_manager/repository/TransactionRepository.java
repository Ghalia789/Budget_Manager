package com.budgetmanager.budget_manager.repository;

import com.budgetmanager.budget_manager.model.Transaction;
import com.budgetmanager.budget_manager.model.TransactionType;
import com.budgetmanager.budget_manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserAndType(User user, TransactionType type);// For filtering income/expense
    List<Transaction> findByUser(User user);  // Custom query to find transactions by user
    List<Transaction> findByUserUserId(Long userId);

    List<Transaction> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate); // For date range filtering

    List<Transaction> findByUserOrderByDateDesc(User user); // Sort transactions by date in descending order

    List<Transaction> findByType(String type);

}
