package com.budgetmanager.budget_manager.repository;
import com.budgetmanager.budget_manager.model.Budget;
import com.budgetmanager.budget_manager.model.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Integer> {
    List<SavingsGoal> findAllByUser_UserId(int userId);

    //List<SavingsGoal> findByCategory_Id(Long categoryId); // Finds savings goals by category ID
}