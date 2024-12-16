package com.budgetmanager.budget_manager.service;

import com.budgetmanager.budget_manager.model.Balance;
import com.budgetmanager.budget_manager.model.User;
import com.budgetmanager.budget_manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // Constructor injection for both UserRepository and BCryptPasswordEncoder
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String rawPassword, String email) {
        String encodedPassword = passwordEncoder.encode(rawPassword);  // Encode password
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setEmail(email);
        user.setTransactions(new ArrayList<>());  // Initialize empty transactions list
        user.setBudgets(new ArrayList<>());        // Initialize empty budgets list
        user.setSavingsGoals(new ArrayList<>());   // Initialize empty savings goals list
        user.setReports(new ArrayList<>());        // Initialize empty reports list
        user.setNotifications(new ArrayList<>());  // Initialize empty notifications list

        // Create and set a default balance
        Balance defaultBalance = new Balance();
        defaultBalance.setAmount(BigDecimal.valueOf(0.0)); // Set initial balance to 0 or any default value
        defaultBalance.setUser(user); // Associate balance with the user

        user.setBalance(defaultBalance);
        userRepository.save(user);  // Save the user
    }

    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        // Check if user is present
        if (userOptional.isPresent()) {
            return userOptional.get();  // Return the User if present
        } else {
            throw new RuntimeException("User not found with username: " + username);
        }
    }
    // Check if the username exists
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}