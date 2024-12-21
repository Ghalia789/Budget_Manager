package com.budgetmanager.budget_manager.controller;

import com.budgetmanager.budget_manager.model.Balance;
import com.budgetmanager.budget_manager.model.User;
import com.budgetmanager.budget_manager.service.BalanceService;
import com.budgetmanager.budget_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;
    @Autowired
    UserService userService;


    // Show balance edit form
    @GetMapping("/edit")
    public String showEditBalanceForm(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        Balance balance = balanceService.getBalanceByUserId(user.getUserId());
                //.orElseThrow(() -> new RuntimeException("Balance not found for user ID: " + userId));
        model.addAttribute("balance", balance);
        return "edit_balance";
    }

    // Process the balance update
    @PostMapping("/update")
    public String updateBalance(@RequestParam("amount") BigDecimal amount) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        balanceService.updateBalance(user.getUserId(), amount);
        return "redirect:/dashboard"; // Redirect to a success page or list
    }
}