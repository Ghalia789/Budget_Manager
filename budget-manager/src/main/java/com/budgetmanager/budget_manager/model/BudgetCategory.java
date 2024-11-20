package com.budgetmanager.budget_manager.model;

public enum BudgetCategory {
    FOOD,
    TRANSPORTATION,
    HOUSING,
    UTILITIES,
    ENTERTAINMENT,
    HEALTHCARE,
    SAVINGS,
    MISCELLANEOUS;

    @Override
    public String toString() {
        // Return a more user-friendly string representation of each category
        return switch (this) {
            case FOOD -> "Food";
            case TRANSPORTATION -> "Transportation";
            case HOUSING -> "Housing";
            case UTILITIES -> "Utilities";
            case ENTERTAINMENT -> "Entertainment";
            case HEALTHCARE -> "Healthcare";
            case SAVINGS -> "Savings";
            case MISCELLANEOUS -> "Miscellaneous";
            default -> super.toString();
        };
    }
}
