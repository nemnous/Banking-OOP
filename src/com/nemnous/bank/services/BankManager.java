package com.nemnous.bank.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.nemnous.bank.exceptions.AccountNotFoundException;
import com.nemnous.bank.exceptions.InsufficientBalanceException;
import com.nemnous.bank.exceptions.InvalidDetailsException;
import com.nemnous.bank.models.Account;
import com.nemnous.bank.models.Bank;
import com.nemnous.bank.models.Customer;
import com.nemnous.bank.models.Details;
import com.nemnous.bank.models.Transaction;

public class BankManager {
	private final Bank bank = new Bank("SBI", "SBI000IFSC");
	private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public void addToBank(Details details) {
		try {
		Account account = new Account(
				new Customer(details.getName(), details.getPhone(), details.getAddress()),
					details.getAccountNumber(), details.getType());
			bank.addAccount(account);
			String accountDetails = account.toString();
			logger.log(Level.INFO, accountDetails);
			logger.log(Level.INFO, "Account Added Succesfully");
		} catch (InvalidDetailsException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}


	public void searchByAccount(String account) {
		try {
			logger.log(Level.INFO, "Account Details"
					+ bank.getAccountById(account));
		} catch (AccountNotFoundException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}

	public void withdrawFromBank(Transaction transaction) {
		try {
			bank.withdrawFromAccount(transaction);
			logger.log(Level.INFO, "Withdrawn Succesfully");
		} catch (AccountNotFoundException
				| InvalidDetailsException
				| InsufficientBalanceException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
		
	}
	
	public void depositInBank(Transaction transaction) {
		try {
			bank.depositInAccount(transaction);
			logger.log(Level.INFO, "Deposited Succesfully");
		} catch (AccountNotFoundException
				| InvalidDetailsException
				| InsufficientBalanceException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}
	
	/**
	 * This function is called when a user requests
	 * to display all the accounts data.
	 */
	public void displayAccounts() {
		for (Account account: bank.getAllAccounts()) {
			String details = account.toString();
			logger.log(Level.INFO, details);
		}
	}
	
}
