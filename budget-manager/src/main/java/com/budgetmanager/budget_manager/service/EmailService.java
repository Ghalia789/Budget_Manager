package com.budgetmanager.budget_manager.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
    public void sendBudgetWiseTipsEmail(String email, String userName) {
        String subject = "BudgetWise Tip: Maximize Your Savings!";
        String body = String.format(
                "Dear %s,\n\n" +
                        "Here's a quick tip to help you save better:\n" +
                        "\"Track every expense, no matter how small. Small leaks can sink great ships!\"\n\n" +
                        "Keep striving for financial freedom with BudgetWise!\n\n" +
                        "Best regards,\nBudgetWise Team",
                userName
        );

        sendEmail(email, subject, body);
    }
    public void sendBudgetLimitWarningEmail(String email, String userName, String budgetCategory, double spentPercentage) {
        String subject = "BudgetWise Alert: Nearing Budget Limit";
        String body = String.format(
                "Dear %s,\n\n" +
                        "You have spent %.2f%% of your allocated budget for the category: %s.\n" +
                        "Please review your expenses to avoid exceeding your budget.\n\n" +
                        "Stay wise with BudgetWise!\n\n" +
                        "Best regards,\nBudgetWise Team",
                userName, spentPercentage, budgetCategory
        );

        sendEmail(email, subject, body);
    }

    public void sendSavingsGoalAchievementEmail(String email, String userName, String goalName, double savedAmount) {
        String subject = "BudgetWise Milestone: Nearing Your Savings Goal!";
        String body = String.format(
                "Dear %s,\n\n" +
                        "Congratulations! You are close to reaching your savings goal: %s.\n" +
                        "You have already saved %.2f. Keep it up!\n\n" +
                        "Achieve more with BudgetWise!\n\n" +
                        "Best regards,\nBudgetWise Team",
                userName, goalName, savedAmount
        );

        sendEmail(email, subject, body);
    }

    /*public void sendBudgetExceededAlert(String email, String budgetName) {
        String subject = "Budget Exceeded Alert!";
        String message = "You have exceeded your budget for " + budgetName + ". Please review your expenses.";
        sendEmail(email, subject, message);
    }

    public void sendSavingsGoalAchievedAlert(String email, String goalName) {
        String subject = "Savings Goal Achieved!";
        String message = "Congratulations! You are close to achieving your savings goal: " + goalName + ".";
        sendEmail(email, subject, message);
    }*/


}