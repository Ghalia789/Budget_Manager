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
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionID;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String description;

    // Optional relationship: expense can be linked to a budget
    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = true)
    private Budget budget;

    // Optional relationship: income can be linked to a savings goal
    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = true)
    private SavingsGoal savingsGoal;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}

