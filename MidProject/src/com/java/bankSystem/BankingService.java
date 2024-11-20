package com.java.bankSystem;

//import com.java.bankingsystem.utils.*;

import java.util.*;

public class BankingService {
	
	private final DatabaseHelper dbHelper = new DatabaseHelper();
    public void createAccount(Scanner scanner) {
        try {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Initial Deposit: ");
            double initialDeposit = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Choose Account Type (1: Savings, 2: Current): ");
            int accountTypeChoice = scanner.nextInt();
            scanner.nextLine();
            String accountType = accountTypeChoice == 1 ? "SAVINGS" : "CURRENT";

            dbHelper.createAccount(username, password, initialDeposit, accountType);
            System.out.println("Account created successfully!");
            
        } catch (Exception e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
        
       }
    
    public User login(Scanner scanner) {
        try {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            User user = dbHelper.authenticateUser(username, password);
            if (user != null) {
                System.out.println("Login successful!");
                return user;
                
            } else {
                System.out.println("Invalid credentials.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return null;
        }
    }

    public void deposit(Scanner scanner, User user) {
        try {
            System.out.print("Enter Account Number: ");
            String accountNumber = scanner.nextLine();
            System.out.print("Enter Deposit Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            dbHelper.performTransaction(accountNumber, amount, "DEPOSIT");
            Logger.logDeposit(accountNumber, amount);
            System.out.println("Deposited $" + amount);
        } catch (Exception e) {
            System.out.println("Deposit error: " + e.getMessage());
        }
    }

    public void withdraw(Scanner scanner, User user) {
        try {
            System.out.print("Enter Account Number: ");
            String accountNumber = scanner.nextLine();
            System.out.print("Enter Withdrawal Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            dbHelper.performTransaction(accountNumber, amount, "WITHDRAWAL");
            Logger.logWithdrawal(accountNumber, amount);
            System.out.println("Withdrew $" + amount);
        } catch (Exception e) {
            System.out.println("Withdrawal error: " + e.getMessage());
        }
    }

    public void viewTransactionHistory(Scanner scanner, User user) {
        try {
            System.out.print("Enter Account Number: ");
            String accountNumber = scanner.nextLine();

            List<Transaction> transactions = dbHelper.getTransactionHistory(accountNumber);
            if (transactions.isEmpty()) {
                System.out.println("No transactions found.");
            } else {
                System.out.println("Transaction History:");
                for (Transaction transaction : transactions) {
                    System.out.println(transaction);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving transaction history: " + e.getMessage());
        }
    }

    public void viewAccountBalance(Scanner scanner, User user) {
        try {
            System.out.print("Enter Account Number: ");
            String accountNumber = scanner.nextLine();
            double balance = dbHelper.getAccountBalance(accountNumber);
            System.out.println("Account Balance: $" + balance);
        } catch (Exception e) {
           System.out.println("Error retrieving account balance: " + e.getMessage());
        }
    }

    public void shutdown() {
        dbHelper.closeConnection();
        //logger.shutdown();
    }
    
}
