package com.budgetmanager.budget_manager.repository;

import com.budgetmanager.budget_manager.model.SavingsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsCategoryRepository extends JpaRepository<SavingsCategory, Long> {
}
