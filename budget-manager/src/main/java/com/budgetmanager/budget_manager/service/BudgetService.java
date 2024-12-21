package com.budgetmanager.budget_manager.service;

import com.budgetmanager.budget_manager.model.Budget;
import com.budgetmanager.budget_manager.model.Transaction;
import com.budgetmanager.budget_manager.repository.BudgetRepository;
import com.budgetmanager.budget_manager.service.errors.BudgetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private EmailService emailService;

    // Create or update a budget
    public Budget saveBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    // Get all budgets
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    // Get a budget by its ID
    public Budget getBudgetById(int id) {
        return budgetRepository.findById((long) id).orElse(null); // Return null if not found
    }

    // Delete a budget by its ID
    public void deleteBudget(int id) {
        if (budgetRepository.existsById((long) id)) {
            budgetRepository.deleteById((long) id);
        } else {
            throw new BudgetNotFoundException("Budget not found"); // You can handle this in the controller
        }
    }

    // Get budgets by user ID
    public List<Budget> getBudgetsByUserId(int userId) {
        return budgetRepository.findAllByUser_UserId( userId); // Assuming the User entity has 'userId'
    }

    // Update a budget's details
    public Budget updateBudget(int id, Budget updatedBudget) {
        Budget existingBudget = budgetRepository.findById((long) id)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found"));

        existingBudget.setCategory(updatedBudget.getCategory());
        existingBudget.setAmountSpent(updatedBudget.getAmountSpent());
        existingBudget.setStartDate(updatedBudget.getStartDate());
        existingBudget.setEndDate(updatedBudget.getEndDate());
        // Calculate the total amount spent from the linked transactions
        double totalSpent = existingBudget.getTransactions().stream()
                .mapToDouble(Transaction::getAmount) // Assuming each Transaction has a getAmount() method
                .sum();

        // Set the total spent amount
        existingBudget.setAmountSpent(totalSpent);
        // Check if budget usage exceeds 90%
        double usagePercentage = (existingBudget.getAmountSpent() / existingBudget.getAmountAllocated()) * 100;
        if (usagePercentage >= 90) {
            // Send an email to notify the user
            emailService.sendBudgetLimitWarningEmail(
                    existingBudget.getUser().getEmail(),
                    existingBudget.getUser().getUsername(),
                    existingBudget.getCategory().name(),
                    usagePercentage
            );
        }

        return budgetRepository.save(existingBudget);
    }

    private void validateBudgetDates(Budget budget) {
        if (budget.getStartDate() != null && budget.getEndDate() != null &&
                budget.getStartDate().isAfter(budget.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }

    public void addTransactionToBudget(int budgetId, Transaction transaction) {
        // Fetch the budget from the repository
        Budget budget = budgetRepository.findById((long) budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        // Initialize the transactions list if necessary
        if (budget.getTransactions() == null) {
            budget.setTransactions(new ArrayList<>());
        }

        // Set the bidirectional relationship
        //transaction.setBudget(budget);

        // Add the transaction to the budget
        budget.getTransactions().add(transaction);

        // Save the transaction and the budget
        //transactionRepository.save(transaction);
        budgetRepository.save(budget); // Optional, depending on cascading settings
    }

}
