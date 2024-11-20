package com.java.bankSystem;

public class Transaction {
	 private int transactionId;
	    private double amount;
	    private String transactionType;
	    private String date;
	    private String accountNumber;

	    public Transaction(int transactionId, double amount, String transactionType, String date, String accountNumber) {
	        this.transactionId = transactionId;
	        this.amount = amount;
	        this.transactionType = transactionType;
	        this.date = date;
	        this.accountNumber = accountNumber;
	    }
	    public int getTransactionId() {
	        return transactionId;
	    }

	    public double getAmount() {
	        return amount;
	    }

	    public String getTransactionType() {
	        return transactionType;
	    }

	    public String getDate() {
	        return date;
	    }

	    public String getAccountNumber() {
	        return accountNumber;
	    }

	    @Override
	    public String toString() {
	        return "Transaction [transactionId=" + transactionId + ", amount=" + amount + 
	               ", transactionType=" + transactionType + ", date=" + date + ", accountNumber=" + accountNumber + "]";
	    }
}
