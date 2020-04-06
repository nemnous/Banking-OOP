package com.nemnous.bank.services;

public class Transaction {
	private final String accountid;
	private final float amount;
	public Transaction(String accountid, float amount) {
		super();
		this.accountid = accountid;
		this.amount = amount;
	}
	public String getAccountid() {
		return accountid;
	}
	public float getAmount() {
		return amount;
	}
}
