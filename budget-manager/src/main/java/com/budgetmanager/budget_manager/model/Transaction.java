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
    private int transactionID;

    private double amount;
    private LocalDate date;
    private String category;

    @Enumerated(EnumType.STRING)
    private TransactionType type; // Enum to define type (Income or Expense)

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

