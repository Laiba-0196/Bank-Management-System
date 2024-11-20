package com.java.bankSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Logger {
	 	private static final String TRANSACTION_LOG_FILE = "transactions.log";
	    private static final String ERROR_LOG_FILE = "error.log";
	    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	    // Log transaction with account number, transaction type, and amount
	    public static void logTransaction(String accountNumber, String transactionType, double amount) {
	        String logMessage = String.format("%s - Account: %s, Type: %s, Amount: $%.2f",
	                dtf.format(LocalDateTime.now()), accountNumber, transactionType, amount);
	        writeToFile(TRANSACTION_LOG_FILE, logMessage);
	    }

	    // Log errors that occur in various parts of the bank system
	    public static void logError(String errorMessage) {
	        String logMessage = String.format("%s - Error: %s", dtf.format(LocalDateTime.now()), errorMessage);
	        writeToFile(ERROR_LOG_FILE, logMessage);
	    }

	    // Method to write log messages to a file
	    private static void writeToFile(String fileName, String message) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
	            writer.write(message);
	            writer.newLine();
	        } catch (IOException e) {
	            System.err.println("Failed to write to log file: " + e.getMessage());
	        }
	    }

	    // Read transaction logs from the file
	    public List<Transaction> readTransactionsFromLogFile()
	    {
	        List<Transaction> transactions = new ArrayList<>();
	        try (BufferedReader br = new BufferedReader(new FileReader(TRANSACTION_LOG_FILE))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] parts = line.split(", ");
	                if (parts.length == 3) {
	                    String[] datePart = parts[0].split(": ");
	                    String[] accountPart = parts[1].split(": ");
	                    String[] typePart = parts[2].split(": \\$");
	                    
	                    String date = datePart[1].trim();
	                    String accountNumber = accountPart[1].trim();
	                    String transactionType = typePart[0].trim();
	                    double amount = Double.parseDouble(typePart[1].trim());

	                    Transaction transaction = new Transaction(0, amount, transactionType, date, accountNumber);
	                    transactions.add(transaction);
	                }
	            }
	        } catch (IOException e) {
	            logError("Error reading transaction log file: " + e.getMessage());
	        }
	        return transactions;
	    }

	    // A method to log deposits and withdrawals (from BankingService)
	    public static void logDeposit(String accountNumber, double amount) {
	        logTransaction(accountNumber, "DEPOSIT", amount);
	    }

	    public static void logWithdrawal(String accountNumber, double amount) {
	        logTransaction(accountNumber, "WITHDRAWAL", amount);
	    }
}
