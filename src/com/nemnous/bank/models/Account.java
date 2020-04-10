package com.nemnous.bank.models;

import com.nemnous.bank.exceptions.InvalidDetailsException;
import com.nemnous.bank.interfaces.Accountable;

/**
 * This class implements the Accountable.
 * @author nemnous
 */
public class Account implements Accountable {
	private Customer customer;
	private String accountNumber;
	private float balance;
	private String typeOfAccount;

	/**
	 * Parameterised constructor.
	 * @param customer
	 * @param accountNumber
	 * @param typeOfAccount
	 */
	public Account(final Customer customer, final String accountNumber, final String typeOfAccount) {
		super();
		this.customer = customer;
		this.accountNumber = accountNumber;
		this.typeOfAccount = typeOfAccount;
		this.balance = 0;
	}

	public Account() {
		/**
		 * Default Constructor
		 */
	}

	/**
	 * Deposits money to the account.
	 * @param amount - amount to be deposited.
	 * @throws Invalid details exception upon depositing amount
	 * less than 0.
	 */
	public void deposit(final float amount) {
		if (amount <= 0) {
			throw new InvalidDetailsException("Deposit money should be greater than 0");
		}
		this.balance += amount;
	}

	/**
	 * Withdraw amount from an account.
	 * @param amount - to be withdrawn.
	 * @throws Invalid details exception upon withdrawing amount
	 * less than 0.
	 * @return true if the withdraw is successful.
	 */
	public boolean withdraw(final float amount) {
		final float minbal = 0;
		if (amount <= 0) {
			throw new InvalidDetailsException("Withdrawl amount cannot be negative");
		}
		if (this.balance - minbal < amount) {
			return false;
		}
		this.balance -= amount;
		return true;
	}

	/**
	 *
	 * @return customer class object.
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 *
	 * @param customer sets customer class object.
	 */
	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	/**
	 *
	 * @return account number.
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * set Account number.
	 * @param accountNumber
	 */
	public void setAccountNumber(final String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return balance.
	 */
	public float getAvailableBalance() {
		return balance;
	}

	/**
	 * sets the balance.
	 * @param balance
	 */
	public void setBalance(final float balance) {
		this.balance = balance;
	}

	/**
	 *
	 * @return type of account
	 */
	public String getTypeOfAccount() {
		return typeOfAccount;
	}

	/**
	 * sets type of account.
	 * @param typeOfAccount
	 */
	public void setTypeOfAccount(final String typeOfAccount) {
		this.typeOfAccount = typeOfAccount;
	}

	/**
	 * To String method for the class.
	 */
	@Override
	public String toString() {
		return customer + ", accountNumber=" + accountNumber + ", balance=" + balance
				+ ", typeOfAccount=" + typeOfAccount;
	}
}

