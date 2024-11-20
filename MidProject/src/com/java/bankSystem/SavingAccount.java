package com.java.bankSystem;

public class SavingAccount extends Account{
	private double interestRate;
	
	public SavingAccount(String accountNumber, double balance, int userId,double interestRate) {
        super(accountNumber, balance, userId);
        this.interestRate = interestRate; 
    }
	public void applyInterest() {
        double interest = getBalance() * interestRate / 100;  
        setBalance(getBalance() + interest); 
        System.out.println("Interest applied: $" + interest);
    }
    @Override
    public String getAccountType() {
        return "SAVINGS";
    }

}
