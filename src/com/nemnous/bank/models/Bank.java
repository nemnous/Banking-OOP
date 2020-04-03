package com.nemnous.bank.models;

import com.nemnous.bank.exceptions.AccountNotFoundException;
import com.nemnous.bank.exceptions.InsufficientBalanceException;
import com.nemnous.bank.exceptions.InvalidDetailsException;
import com.nemnous.bank.interfaces.Bankable;

import java.util.ArrayList;
import java.util.List;

public class Bank implements Bankable {
	private String name;
	private String ifsc;
	private List<Account> accounts;

	/**
	 * Parameterized constructor.
	 * @param name
	 * @param ifsc
	 */
	public Bank(final String name, final String ifsc) {
		super();
		this.name = name;
		this.ifsc = ifsc;
		this.accounts = new ArrayList<Account>();
	}

	/**
	 * Get's the account by account number from Bank.
	 * @param id account number.
	 * @throws Invalid details exception and
	 *  Account doesn't Exist Exception.
	 *  @return Account object.
	 */
	public Account getAccountById(final String id) throws InvalidDetailsException, AccountNotFoundException {
		if (id == null || id == "") {
			throw new InvalidDetailsException("Account number cannot be empty");
		}
		for (Account acc : this.accounts) {
			if (acc.getAccountNumber().equals(id)) {
				return acc;
			}
		}
		throw new AccountNotFoundException("Account Doesnt Exist");

	}

	/**
	 * Deposits the amount to the given account number.
	 * @param id account number.
	 * @param amount amount to be deposited.
	 * @throws Invalid details exception when account number is null
	 * and Account not found exception when account doesn't exist.
	 */
	public void depositInAccount(final String id, final float amount) throws InvalidDetailsException,
	AccountNotFoundException {
		if (id == null || id == "") {
			throw new InvalidDetailsException("Account number cannot be empty");
		}
		Account tempAccount = null;
		for (Account account : this.accounts) {
			if (account.getAccountNumber().equals(id)) {
				tempAccount = account;
			}
		}
		if (tempAccount == null) {
			//Should raise Exception Invalid Account
			throw new AccountNotFoundException("Account doesnt Exist");
		}

		tempAccount.deposit(amount);
	}

	/**
	 * @param id Account Number.
	 * @param amount Amount to withdraw.
	 * @throws Account Not found exception
	 * Invalid details Exception.
	 * Insufficient balance Exception.
	 */
	public void withdrawFromAccount(final String id, final float amount) throws AccountNotFoundException,
	InsufficientBalanceException, InvalidDetailsException {
		if (id == null || id == "") {
			throw new InvalidDetailsException("Account number cannot be empty");
		}
		Account tempAccount = null;
		for (Account account : this.accounts) {
			if (account.getAccountNumber().equals(id)) {
				tempAccount = account;
			}
		}
		if (tempAccount == null) {
			//Should raise Exception Invalid Account
			throw new AccountNotFoundException("Account Doesnt Exist");
		}

		if (!tempAccount.withdraw(amount)) {
			//Should raise Insufficient Balance Account
			throw new InsufficientBalanceException("Insufficient Balance");
		}

	}

	/**
	 * @param acc account object to add.
	 */
	public void addAccount(final Account acc) {
		this.accounts.add(acc);
	}

	/**
	 * @return List of all accounts.
	 */
	public List<Account> getAllAccounts() {
		return accounts;
	}

	/**
	 * @param id Account number.
	 * @return true if the account deleted successfully.
	 */
	public boolean removeAccount(final String id) {
		if (id == null || id == "") {
			throw new InvalidDetailsException("Account number cannot be empty");
		}
		Account tempAccount = null;
		for (Account account : this.accounts) {
			if (account.getAccountNumber().equals(id)) {
				tempAccount = account;
				break;
			}
		}
		if (tempAccount != null) {
			return accounts.remove(tempAccount);
		}
		return false;

	}

	/**
	 *
	 * @return private variable name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets private variable name.
	 * @param name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 *
	 * @return private variable ifsc Code
	 */
	public String getIfsc() {
		return this.ifsc;
	}

	/**
	 * sets private variable ifsc code.
	 * @param ifsc
	 */
	public void setIfsc(final String ifsc) {
		this.ifsc = ifsc;
	}

}
