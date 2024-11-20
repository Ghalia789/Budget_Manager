package com.budgetmanager.budget_manager.service.errors;
public class BudgetNotFoundException extends RuntimeException {
    public BudgetNotFoundException(String message) {
        super(message);
    }
}