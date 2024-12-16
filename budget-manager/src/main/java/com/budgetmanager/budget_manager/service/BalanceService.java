package com.budgetmanager.budget_manager.service;

import com.budgetmanager.budget_manager.model.Balance;
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

    // Get all balances
    public List<Balance> getAllBalances() {
        return balanceRepository.findAll();
    }

    // Get a balance by its ID
    public Balance getBalanceById(Long id) {
        return balanceRepository.findById(id).orElse(null);
    }

    // Delete a balance by its ID
    public void deleteBalance(Long id) {
        if (balanceRepository.existsById(id)) {
            balanceRepository.deleteById(id);
        } else {
            throw new RuntimeException("Balance not found");
        }
    }

    // Get balance by user ID
    public Balance getBalanceByUserId(int userId) {
        return balanceRepository.findByUser_UserId(userId);
    }

    // Update balance amount
    public Balance updateBalanceAmount(Long id, BigDecimal newAmount) {
        Optional<Balance> optionalBalance = balanceRepository.findById(id);
        if (optionalBalance.isEmpty()) {
            throw new RuntimeException("Balance not found");
        }
        Balance balance = optionalBalance.get();
        balance.setAmount(newAmount);
        return balanceRepository.save(balance);
    }
}