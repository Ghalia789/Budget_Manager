package com.budgetmanager.budget_manager.service;

import com.budgetmanager.budget_manager.model.SavingsCategory;
import com.budgetmanager.budget_manager.repository.SavingsCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SavingsCategoryService {
    @Autowired
    private SavingsCategoryRepository savingsCategoryRepository;

    // Create or update a savings category
    public SavingsCategory saveSavingsCategory(SavingsCategory savingsCategory) {
        return savingsCategoryRepository.save(savingsCategory);
    }

    // Get all savings categories
    public List<SavingsCategory> getAllSavingsCategories() {
        return savingsCategoryRepository.findAll();
    }

    // Get all savings categories for a specific user
    public List<SavingsCategory> getAllSavingsCategoriesByUserId(int userId) {
        return savingsCategoryRepository.findAllByUser_UserId(userId);
    }

    // Get a savings category by its ID
    public SavingsCategory getSavingsCategoryById(Long id) {
        return savingsCategoryRepository.findById(id).orElse(null);
    }

    // Delete a savings category by ID
    public void deleteSavingsCategory(Long id) {
        if (savingsCategoryRepository.existsById(id)) {
            savingsCategoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Savings Category not found");
        }
    }


}
