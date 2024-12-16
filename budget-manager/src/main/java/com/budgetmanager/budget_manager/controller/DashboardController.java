package com.budgetmanager.budget_manager.controller;

import com.budgetmanager.budget_manager.model.Balance;
import com.budgetmanager.budget_manager.model.User;
import com.budgetmanager.budget_manager.service.BalanceService;
import com.budgetmanager.budget_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {
    @Autowired
    private BalanceService balanceService;

    @Autowired
    private UserService userService;
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Fetch the currently authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);  // Assume you have a service to get user by username
        Balance balance = balanceService.getBalanceByUserId(user.getUserId());

        // Add data to the model for Thymeleaf rendering
        model.addAttribute("username", username);
        model.addAttribute("balance", balance);

        return "dashboard"; // Thymeleaf template name
    }

    // Handle balance update
    @PostMapping("/updateBalance")
    public String updateBalance(@ModelAttribute Balance balance, Model model) {
        // Call BalanceService to update the balance
        balanceService.updateBalanceAmount(balance.getId(), balance.getAmount());

        // After updating, retrieve the updated balance and send it back to the frontend
        model.addAttribute("balance", balanceService.getBalanceByUserId(balance.getUser().getUserId()));

        return "redirect:/dashboard";  // Redirect back to the dashboard to show updated balance
    }
}
