package com.budgetmanager.budget_manager.service;

import com.budgetmanager.budget_manager.model.Budget;
import com.budgetmanager.budget_manager.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

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
            throw new RuntimeException("Budget not found"); // You can handle this in the controller
        }
    }

    // Get budgets by user ID
    public List<Budget> getBudgetsByUserId(int userId) {
        return budgetRepository.findAllByUser_UserId((long) userId); // Assuming the User entity has 'userId'
    }

    // Update a budget's details
    public Budget updateBudget(int id, Budget updatedBudget) {
        if (!budgetRepository.existsById((long) id)) {
            throw new RuntimeException("Budget not found"); // You can handle this in the controller
        }

        updatedBudget.setBudgetID(id); // Ensure the budget ID is set to the correct value
        return budgetRepository.save(updatedBudget); // Save the updated budget
    }

    //these are in case of using an external frontend framework making this an api
    // Create or update a budget
    /*public Budget saveBudget(Budget budget) {
        return budgetRepository.save(budget);
    }
    // Create or update a budget
    public ResponseEntity<Budget> saveorupdateBudget(Budget budget) {
        if (budget == null) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if input is null
        }

        // Save and return the budget with status 201 Created
        Budget savedBudget = budgetRepository.save(budget);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBudget);
    }

    // Get all budgets
    public ResponseEntity<List<Budget>> getAllBudgets() {
        List<Budget> budgets = budgetRepository.findAll();
        if (budgets.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no budgets exist
        }
        return ResponseEntity.ok(budgets); // Return 200 OK with the list of budgets
    }
    // Get a budget by its ID
    public ResponseEntity<Budget> getBudgetById(int id) {
        Optional<Budget> budgetOptional = budgetRepository.findById((long) id);
        // Return 200 OK with the budget if found
        // Return 404 Not Found if the budget doesn't exist
        return budgetOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a budget by its ID
    public ResponseEntity<Void> deleteBudget(int id) {
        if (!budgetRepository.existsById((long) id)) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if the budget doesn't exist
        }

        // Delete the budget and return 204 No Content
        budgetRepository.deleteById((long) id);
        return ResponseEntity.noContent().build();
    }

    // Get budgets by user ID
    public ResponseEntity<List<Budget>> getBudgetsByUserId(int userId) {
        List<Budget> budgets = budgetRepository.findAllByUser_UserId((long) userId); // Assuming the User entity has 'userId'
        if (budgets.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no budgets exist for the user
        }
        return ResponseEntity.ok(budgets); // Return 200 OK with the list of budgets
    }

    // Update a budget's details
    public ResponseEntity<Budget> updateBudget(int id, Budget updatedBudget) {
        // Check if the budget exists
        if (!budgetRepository.existsById((long) id)) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if the budget doesn't exist
        }

        updatedBudget.setBudgetID(id); // Ensure the budget ID is set to the correct value
        Budget savedBudget = budgetRepository.save(updatedBudget); // Save the updated budget
        return ResponseEntity.ok(savedBudget); // Return 200 OK with the updated budget
    }*/

}
