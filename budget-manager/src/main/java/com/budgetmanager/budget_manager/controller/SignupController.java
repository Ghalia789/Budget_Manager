package com.budgetmanager.budget_manager.controller;

import com.budgetmanager.budget_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    private final UserService userService;

    // Constructor injection of UserService
    public SignupController(UserService userService) {
        this.userService = userService;
    }
    // Display the sign-up form
    @GetMapping("/signup")
    public String showSignUpForm() {
        return "signup"; // Return the signup.html page
    }

    // Handle the form submission for creating a new user
    @PostMapping("/signup")
    public String signUp(@RequestParam String username,
                         @RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String confirmPassword,
                         Model model) {

        // Validate password confirmation
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "signup"; // Return to the sign-up page with an error message
        }

        try {
            // Create and register the new user
            userService.registerUser(username, password, email);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "signup"; // Return to the sign-up page with the error message
        }

        // Redirect to the login page or another page after successful registration
        return "redirect:/login";
    }
}