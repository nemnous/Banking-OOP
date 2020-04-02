package Models;

import AbstractClassModel.AbstractAccount;

public class Account extends AbstractAccount{
	private Customer customer;
	private String accountNumber;
	private float balance;
	private String typeOfAccount;
	
	public Account(Customer customer, String accountNumber, String typeOfAccount) {
		super();
		this.customer = customer;
		this.accountNumber = accountNumber;
		this.typeOfAccount = typeOfAccount;
		this.balance = 0;
	}
	
	public void deposit(float amount) {
		this.balance += amount;
	}
	
	public boolean withdraw(float amount) {
		float minbal = 0;
		if (this.balance - minbal < amount) {
			return false;
		}
		this.balance -= amount;
		return true;
		
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public float getAvailableBalance() {
		return balance;
	}
	
	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	public String getTypeOfAccount() {
		return typeOfAccount;
	}
	
	public void setTypeOfAccount(String typeOfAccount) {
		this.typeOfAccount = typeOfAccount;
	}

	@Override
	public String toString() {
		return customer + ", accountNumber=" + accountNumber + ", balance=" + balance
				+ ", typeOfAccount=" + typeOfAccount;
	}
	
	
	
	

}
