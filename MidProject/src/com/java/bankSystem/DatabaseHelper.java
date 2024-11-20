package com.java.bankSystem;

//import com.java.bankingsystem.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
	
	    private Connection connection;

	    public DatabaseHelper() {
	        try {
	            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank2", "root", "@Laiba101010");
	        } catch (SQLException e) {
	            throw new RuntimeException("Failed to connect to the database.", e);
	        }
	    }

	    public void createAccount(String username, String password, double initialDeposit, String accountType) throws SQLException {
	        String createUserQuery = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
	        String createAccountQuery = "INSERT INTO Accounts (AccountNumber, Balance, UserID, AccountType) VALUES (?, ?, ?, ?)";
	        String accountNumber = generateAccountNumber();

	        try (PreparedStatement userStmt = connection.prepareStatement(createUserQuery, Statement.RETURN_GENERATED_KEYS);
	             PreparedStatement accountStmt = connection.prepareStatement(createAccountQuery)) {

	            userStmt.setString(1, username);
	            userStmt.setString(2, password);
	            userStmt.executeUpdate();

	            ResultSet generatedKeys = userStmt.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int userId = generatedKeys.getInt(1);

	                accountStmt.setString(1, accountNumber);
	                accountStmt.setDouble(2, initialDeposit);
	                accountStmt.setInt(3, userId);
	                accountStmt.setString(4, accountType);
	                accountStmt.executeUpdate();
	            }
	        }
	    }
	    
	    public User authenticateUser(String username, String password) throws SQLException {
	        String query = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setString(1, username);
	            stmt.setString(2, password);
	            ResultSet resultSet = stmt.executeQuery();
	            if (resultSet.next()) {
	                return new User(resultSet.getInt("UserID"), username, password);
	            }
	        }
	        return null;
	    }
	    
	    public void performTransaction(String accountNumber, double amount, String transactionType) throws SQLException {
	        String updateBalanceQuery = transactionType.equals("DEPOSIT")
	                ? "UPDATE Accounts SET Balance = Balance + ? WHERE AccountNumber = ?"
	                : "UPDATE Accounts SET Balance = Balance - ? WHERE AccountNumber = ?";
	        String insertTransactionQuery = "INSERT INTO Transactions (AccountNumber, Amount, TransactionType) VALUES (?, ?, ?)";

	        try (PreparedStatement balanceStmt = connection.prepareStatement(updateBalanceQuery);
	             PreparedStatement transactionStmt = connection.prepareStatement(insertTransactionQuery)) {

	            balanceStmt.setDouble(1, amount);
	            balanceStmt.setString(2, accountNumber);
	            if (balanceStmt.executeUpdate() == 0) {
	                throw new SQLException("Account not found or insufficient funds.");
	            }
	            transactionStmt.setString(1, accountNumber);
	            transactionStmt.setDouble(2, amount);
	            transactionStmt.setString(3, transactionType);
	            transactionStmt.executeUpdate();
	        }
	    }

	    public List<Transaction> getTransactionHistory(String accountNumber) throws SQLException {
	        List<Transaction> transactions = new ArrayList<>();
	        String query = "SELECT * FROM Transactions WHERE AccountNumber = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setString(1, accountNumber);
	            ResultSet resultSet = stmt.executeQuery();
	            while (resultSet.next()) {
	                transactions.add(new Transaction(
	                        resultSet.getInt("TransactionID"),
	                        resultSet.getDouble("Amount"),
	                        resultSet.getString("TransactionType"),
	                        resultSet.getString("Date"),
	                        resultSet.getString("AccountNumber")
	                ));
	            }
	        }
	        return transactions;
	    }
	    
	    public double getAccountBalance(String accountNumber) throws SQLException {
	        String query = "SELECT Balance FROM Accounts WHERE AccountNumber = ?";
	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            stmt.setString(1, accountNumber);
	            ResultSet resultSet = stmt.executeQuery();
	            if (resultSet.next()) {
	                return resultSet.getDouble("Balance");
	            } else {
	                throw new SQLException("Account not found.");
	            }
	        }
	    }

	    public void closeConnection() {
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException("Error closing database connection.", e);
	        }
	    }

	    private String generateAccountNumber() {
	        return "ACCT" + System.currentTimeMillis();
	    }

}
