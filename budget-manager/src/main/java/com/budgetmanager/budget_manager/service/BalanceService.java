package com.budgetmanager.budget_manager.service;

import com.budgetmanager.budget_manager.model.Balance;
import com.budgetmanager.budget_manager.model.Transaction;
import com.budgetmanager.budget_manager.model.TransactionType;
import com.budgetmanager.budget_manager.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BalanceService {
    @Autowired
    private BalanceRepository balanceRepository;

    // Save or update balance
    public Balance saveBalance(Balance balance) {
        return balanceRepository.save(balance);
    }


    // Get balance by user ID
    public Balance getBalanceByUserId(int userId) {
        return balanceRepository.findByUser_UserId(userId);
    }


    // Update balance for a user
    public void updateBalance(int userId, BigDecimal newAmount) {
        Balance balance = balanceRepository.findByUser_UserId(userId);
        if (balance != null) {
            balance.setAmount(newAmount);
            balanceRepository.save(balance);
        } else {
            throw new RuntimeException("Balance not found for user ID: " + userId);
        }
    }
    public void updateBalanceBasedOnTransaction(int userId, Transaction transaction) {
        // Retrieve the balance for the user
        Balance balance = balanceRepository.findByUser_UserId(userId);
        if (balance == null) {
            throw new RuntimeException("Balance not found for user ID: " + userId);
        }

        // Update the balance amount based on the transaction type
        BigDecimal currentAmount = balance.getAmount();
        BigDecimal transactionAmount = BigDecimal.valueOf(transaction.getAmount());

        if (transaction.getType() == TransactionType.INCOME) {
            // Add the transaction amount for income
            balance.setAmount(currentAmount.add(transactionAmount));
        } else if (transaction.getType() == TransactionType.EXPENSE) {
            // Subtract the transaction amount for expense
            balance.setAmount(currentAmount.subtract(transactionAmount));
        } else {
            throw new IllegalArgumentException("Unknown transaction type: " + transaction.getType());
        }

        // Save the updated balance
        balanceRepository.save(balance);
    }
}