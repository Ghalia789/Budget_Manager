package com.budgetmanager.budget_manager.controller;

import com.budgetmanager.budget_manager.model.SavingsCategory;
import com.budgetmanager.budget_manager.model.SavingsGoal;
import com.budgetmanager.budget_manager.model.User;
import com.budgetmanager.budget_manager.service.SavingsCategoryService;
import com.budgetmanager.budget_manager.service.SavingsGoalService;
import com.budgetmanager.budget_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/savingsgoals")
public class SavingsGoalController {
    @Autowired
    private SavingsGoalService savingsGoalService;

    @Autowired
    private SavingsCategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String listGoals(Model model) {
        User currentUser = userService.getLoggedInUser();
        model.addAttribute("savingsGoals", savingsGoalService.getSavingsGoalsByUserId(currentUser.getUserId()));
        return "savingsGoals";
    }

    @GetMapping("/add")
    public String addGoalForm(Model model) {
        User currentUser = userService.getLoggedInUser();
        model.addAttribute("categories", categoryService.getAllSavingsCategoriesByUserId(currentUser.getUserId()));
        model.addAttribute("savingsGoal", new SavingsGoal());
        return "add-savings-goal";
    }

    @PostMapping("/add")
    public String saveGoal(@ModelAttribute SavingsGoal goal) {
        goal.setUser(userService.getLoggedInUser());
        savingsGoalService.saveSavingsGoal(goal);
        return "redirect:/savingsgoals/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditSavingsGoalForm(@PathVariable int id, Model model) {
        SavingsGoal savingsGoal = savingsGoalService.getSavingsGoalById(id);
        User currentUser = userService.getLoggedInUser();
        List<SavingsCategory> categories = categoryService.getAllSavingsCategoriesByUserId(currentUser.getUserId());
        model.addAttribute("savingsGoal", savingsGoal);
        model.addAttribute("categories", categoryService.getAllSavingsCategoriesByUserId(currentUser.getUserId()));
        return "edit-savings-goal";
    }

    @PostMapping("/edit/{id}")
    public String editSavingsGoal(@PathVariable int id, @ModelAttribute SavingsGoal savingsGoal) {
        savingsGoalService.updateSavingsGoal(id,savingsGoal);
        return "redirect:/savingsgoals/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteSavingsGoal(@PathVariable("id") int id) {
        savingsGoalService.deleteSavingsGoal(id);
        return "redirect:/savingsgoals/list";
    }
}
