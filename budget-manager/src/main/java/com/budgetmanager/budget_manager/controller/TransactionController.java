package com.budgetmanager.budget_manager.controller;

import com.budgetmanager.budget_manager.model.*;
import com.budgetmanager.budget_manager.service.BudgetService;
import com.budgetmanager.budget_manager.service.SavingsGoalService;
import com.budgetmanager.budget_manager.service.TransactionService;
import com.budgetmanager.budget_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private SavingsGoalService savingsGoalService;

    @Autowired
    private UserService userService;


    @GetMapping
    public String listTransactions(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        List<Transaction> transactions = transactionService.getAllTransactions(user);
        model.addAttribute("transactions", transactions);
        return "transactions";
    }
    // Display transaction form
    @GetMapping("/add")
    public String showAddTransactionForm(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        List<Budget> budgets = budgetService.getAllBudgets();
        List<SavingsGoal> goals = savingsGoalService.getAllSavingsGoals();

        model.addAttribute("transaction", new Transaction());
        model.addAttribute("budgets", budgetService.getBudgetsByUserId(user.getUserId()));
        model.addAttribute("goals", savingsGoalService.getSavingsGoalsByUserId(user.getUserId()));
        model.addAttribute("types", TransactionType.values());
        return "add-transaction";
    }

    // Handle form submission
    @PostMapping("/add")
    public String addTransaction(@ModelAttribute Transaction transaction,
                                 @RequestParam(required = false) Integer budgetID,
                                 @RequestParam(required = false) Integer goalID) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        if (transaction.getType() == TransactionType.EXPENSE && budgetID != null) {
            transaction.setBudget(budgetService.getBudgetById(budgetID));
        } else if (transaction.getType() == TransactionType.INCOME && goalID != null) {
            transaction.setSavingsGoal(savingsGoalService.getSavingsGoalById(goalID));
        }
        transaction.setUser(user);
        transactionService.addTransaction(transaction);
        return "redirect:/transactions";
    }


}
