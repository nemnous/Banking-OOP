package com.nemnous.bank.models;

import com.nemnous.bank.exceptions.InvalidDetailsException;

public class Transaction {
	private final String accountid;
	private final float amount;
	public Transaction(String accountid, float amount) {
		super();
		this.accountid = accountid;
		if(amount < 0) {
			throw new InvalidDetailsException("Transaction amount cannot be negative");
		}
		this.amount = amount;
	}
	public String getAccountid() {
		return accountid;
	}
	public float getAmount() {
		return amount;
	}
}
