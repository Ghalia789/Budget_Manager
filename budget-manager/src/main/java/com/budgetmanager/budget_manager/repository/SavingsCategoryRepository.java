package com.budgetmanager.budget_manager.repository;

import com.budgetmanager.budget_manager.model.SavingsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingsCategoryRepository extends JpaRepository<SavingsCategory, Long> {
    List<SavingsCategory> findAllByUser_UserId(int userId);

}
