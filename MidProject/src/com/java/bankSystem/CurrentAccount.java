package com.java.bankSystem;

public class CurrentAccount extends Account {
	public CurrentAccount(String accountNumber, double balance, int userId) {
        super(accountNumber, balance, userId);
    }

    @Override
    public String getAccountType() {
        return "CURRENT";
    }

}
