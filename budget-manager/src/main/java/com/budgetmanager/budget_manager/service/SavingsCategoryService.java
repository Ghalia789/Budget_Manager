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

    // Get a savings category by ID
    public SavingsCategory getSavingsCategoryById(int id) {
        return savingsCategoryRepository.findById((long) id).orElse(null);
    }

    // Delete a savings category by ID
    public void deleteSavingsCategory(int id) {
        if (savingsCategoryRepository.existsById((long) id)) {
            savingsCategoryRepository.deleteById((long) id);
        } else {
            throw new RuntimeException("Savings Category not found");
        }
    }

    //these below are in case of using an external frontend making this an API
    // Create or update a savings category
    /*public ResponseEntity<SavingsCategory> saveSavingsCategory(SavingsCategory savingsCategory) {
        if (savingsCategory == null) {
            return ResponseEntity.badRequest().build();
        }

        SavingsCategory savedCategory = savingsCategoryRepository.save(savingsCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    // Get all savings categories
    public ResponseEntity<List<SavingsCategory>> getAllSavingsCategories() {
        List<SavingsCategory> categories = savingsCategoryRepository.findAll();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }

    // Get a savings category by ID
    public ResponseEntity<SavingsCategory> getSavingsCategoryById(int id) {
        Optional<SavingsCategory> categoryOptional = savingsCategoryRepository.findById((long) id);
        if (categoryOptional.isPresent()) {
            return ResponseEntity.ok(categoryOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a savings category by ID
    public ResponseEntity<Void> deleteSavingsCategory(int id) {
        if (!savingsCategoryRepository.existsById((long) id)) {
            return ResponseEntity.notFound().build();
        }
        savingsCategoryRepository.deleteById((long) id);
        return ResponseEntity.noContent().build();
    }*/


}
