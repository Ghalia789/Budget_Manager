package com.budgetmanager.budget_manager.service;

import com.budgetmanager.budget_manager.model.GoalStatus;
import com.budgetmanager.budget_manager.model.SavingsGoal;
import com.budgetmanager.budget_manager.model.Transaction;
import com.budgetmanager.budget_manager.repository.SavingsGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SavingsGoalService {
    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    // Save or update a savings goal
    public SavingsGoal saveSavingsGoal(SavingsGoal savingsGoal) {
        savingsGoal.setStatus(GoalStatus.IN_PROGRESS);
        return savingsGoalRepository.save(savingsGoal);
    }

    // Get all savings goals
    public List<SavingsGoal> getAllSavingsGoals() {
        return savingsGoalRepository.findAll();
    }

    // Get a savings goal by its ID
    public SavingsGoal getSavingsGoalById(int id) {
        return savingsGoalRepository.findById(id).orElse(null); // Return null if not found
    }

    // Delete a savings goal by its ID
    public void deleteSavingsGoal(int id) {
        if (savingsGoalRepository.existsById(id)) {
            savingsGoalRepository.deleteById(id);
        } else {
            throw new RuntimeException("Savings Goal not found");
        }
    }

    // Get savings goals by user ID
    public List<SavingsGoal> getSavingsGoalsByUserId(int userId) {
        return savingsGoalRepository.findAllByUser_UserId(userId); // Find savings goals by user ID
    }

    // Get savings goals by category ID
   /* public List<SavingsGoal> getSavingsGoalsByCategoryId(Long categoryId) {
        return savingsGoalRepository.findByCategory_Id(categoryId); // Find savings goals by category ID
    }*/

    // Update a savings goal's details
    public void updateSavingsGoal(int id, SavingsGoal updatedGoal) {
        if (!savingsGoalRepository.existsById(id)) {
            throw new RuntimeException("Savings Goal not found");
        }
        updatedGoal.setCurrentAmount(updatedGoal.getCurrentAmount());
        double totalSaved = getSavingsGoalById(id).getTransactions().stream()
                .mapToDouble(Transaction::getAmount) // Assuming each Transaction has a getAmount() method
                .sum();
        updatedGoal.setCurrentAmount(totalSaved);
        savingsGoalRepository.save(updatedGoal);
    }

    public void addTransactionToSavingsGoal(int goalId, Transaction transaction) {
        // Fetch the budget from the repository
        SavingsGoal goal = savingsGoalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Savings Goal not found"));

        // Initialize the transactions list if necessary
        if (goal.getTransactions() == null) {
            goal.setTransactions(new ArrayList<>());
        }

        // Set the bidirectional relationship


        // Add the transaction to the budget
        goal.getTransactions().add(transaction);

        // Save the transaction
        //transactionRepository.save(transaction);
        updateSavingsGoal(goalId,goal);
    }
}
