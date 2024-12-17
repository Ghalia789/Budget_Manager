package com.budgetmanager.budget_manager.service;

import com.budgetmanager.budget_manager.model.SavingsGoal;
import com.budgetmanager.budget_manager.repository.SavingsGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingsGoalService {
    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    // Save or update a savings goal
    public SavingsGoal saveSavingsGoal(SavingsGoal savingsGoal) {
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
    public SavingsGoal updateSavingsGoal(int id, SavingsGoal updatedGoal) {
        if (!savingsGoalRepository.existsById(id)) {
            throw new RuntimeException("Savings Goal not found");
        }

        updatedGoal.setGoalID(id); // Ensure the goal ID is set to the correct value
        return savingsGoalRepository.save(updatedGoal); // Save the updated savings goal
    }
}
