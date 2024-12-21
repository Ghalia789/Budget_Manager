package com.budgetmanager.budget_manager.controller;

import com.budgetmanager.budget_manager.model.*;
import com.budgetmanager.budget_manager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @Autowired
    private BalanceService balanceService;

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
        List<Budget> budgets = budgetService.getBudgetsByUserId(user.getUserId());
        List<SavingsGoal> goals = savingsGoalService.getSavingsGoalsByUserId(user.getUserId());

        model.addAttribute("transaction", new Transaction());
        model.addAttribute("budgets", budgets);
        model.addAttribute("goals", goals);
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
            Budget updatedB= budgetService.getBudgetById(budgetID);
            budgetService.addTransactionToBudget(budgetID,transaction);

            budgetService.updateBudget(budgetID,updatedB);


        } else if (transaction.getType() == TransactionType.INCOME && goalID != null) {
            transaction.setSavingsGoal(savingsGoalService.getSavingsGoalById(goalID));
            SavingsGoal updatedS= savingsGoalService.getSavingsGoalById(goalID);
            savingsGoalService.addTransactionToSavingsGoal(goalID,transaction);
            savingsGoalService.updateSavingsGoal(goalID,updatedS);
        }
        transaction.setUser(user);
        transactionService.addTransaction(transaction);
        balanceService.updateBalanceBasedOnTransaction(user.getUserId(), transaction);
        return "redirect:/transactions";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        int userID= userService.getLoggedInUser().getUserId();
        Transaction transaction = transactionService.getTransactionById(id);
        model.addAttribute("transaction", transaction);
        model.addAttribute("types", List.of("INCOME", "EXPENSE"));
        model.addAttribute("budgets", budgetService.getBudgetsByUserId(userID));
        model.addAttribute("goals", savingsGoalService.getSavingsGoalsByUserId(userID));
        return "edit-transaction";
    }

    @PostMapping("/update")
    public String updateTransaction(@ModelAttribute("transaction") Transaction transaction,
                                    @RequestParam(required = false) Integer budgetID,
                                    @RequestParam(required = false) Integer goalID) {
        if (budgetID != null) {
            transaction.setBudget(budgetService.getBudgetById(budgetID));
        }

        if (goalID != null) {
            transaction.setSavingsGoal(savingsGoalService.getSavingsGoalById(goalID));
        }
        transactionService.updateTransaction(Math.toIntExact(transaction.getTransactionID()),transaction);
        return "redirect:/transactions";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable("id") int id) {
        transactionService.deleteTransaction(id);
        return "redirect:/transactions";
    }
}
