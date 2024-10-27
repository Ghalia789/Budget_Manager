package com.budgetmanager.budget_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingsGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int goalID;

    private String goalName;
    private double targetAmount;
    private double currentAmount;
    private LocalDate targetDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private SavingsCategory category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
