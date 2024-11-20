package com.java.bankSystem;
import java.util.Scanner;

public class BankSystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BankingService bankingService = new BankingService();
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        System.out.println("Welcome to the Banking System");
        while (true) {
            if (currentUser == null) {
                System.out.println("1. Login");
                System.out.println("2. Create Account");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> currentUser = bankingService.login(scanner);
                    case 2 -> bankingService.createAccount(scanner);
                    case 3 -> {
                        bankingService.shutdown();
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } else {
            	
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. View Transaction History");
                System.out.println("4. View Account Balance");
                System.out.println("5. Logout");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> bankingService.deposit(scanner, currentUser);
                    case 2 -> bankingService.withdraw(scanner, currentUser);
                    case 3 -> bankingService.viewTransactionHistory(scanner, currentUser);
                    case 4 -> bankingService.viewAccountBalance(scanner, currentUser);
                    case 5 -> currentUser = null;
                    default -> System.out.println("Invalid option. Try again.");
                }
            }
        }

	}

}
