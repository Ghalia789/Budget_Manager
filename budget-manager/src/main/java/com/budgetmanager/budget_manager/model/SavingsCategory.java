package com.budgetmanager.budget_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingsCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;

    @Column(nullable = false, unique = true)
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
