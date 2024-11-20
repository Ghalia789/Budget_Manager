package com.budgetmanager.budget_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String username;
    private String password;
    private String email;
    @Nullable
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
    @Nullable
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Budget> budgets;
    @Nullable
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SavingsGoal> savingsGoals;
    @Nullable
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Report> reports;
    @Nullable
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private Balance balance; // Association to the Balance entity
}
