package com.budgetmanager.budget_manager.controller;

import com.budgetmanager.budget_manager.model.Balance;
import com.budgetmanager.budget_manager.model.Transaction;
import com.budgetmanager.budget_manager.model.User;
import com.budgetmanager.budget_manager.service.BalanceService;
import com.budgetmanager.budget_manager.service.TransactionService;
import com.budgetmanager.budget_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private BalanceService balanceService;

    @Autowired
    TransactionService transactionService;


    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Fetch the currently authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);  // Assume you have a service to get user by username
        Balance balance = balanceService.getBalanceByUserId(user.getUserId());
        List<Transaction> latestTransactions = transactionService.getLatestTransactions(user);  // Fetch latest 3 transactions

        model.addAttribute("latestTransactions", latestTransactions);  // Add them to the model
        model.addAttribute("username", username);
        model.addAttribute("balance", balance);

        return "dashboard";
    }
    @GetMapping("/reports")
    public String showReports(Model model) {
        return "reports";
    }
}
