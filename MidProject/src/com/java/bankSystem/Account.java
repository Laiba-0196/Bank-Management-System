package com.java.bankSystem;

public abstract class Account {
		private String accountNumber;
	    private double balance;
	    private int userId;

	    public Account(String accountNumber, double balance, int userId) {
	        this.accountNumber = accountNumber;
	        this.balance = balance;
	        this.userId = userId;
	    }

	    public String getAccountNumber() {
	        return accountNumber;
	    }

	    public double getBalance() {
	        return balance;
	    }

	    public void setBalance(double balance) {
	        this.balance = balance;
	    }

	    public int getUserId() {
	        return userId;
	    }

	    public abstract String getAccountType();

	    @Override
	    public String toString() {
	        return "Account [accountNumber=" + accountNumber + ", balance=" + balance + ", userId=" + userId + "]";
	    }

}
