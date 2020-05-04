package com.nemnous.bank.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nemnous.bank.exceptions.AccountNotFoundException;
import com.nemnous.bank.exceptions.InsufficientBalanceException;
import com.nemnous.bank.exceptions.InvalidDetailsException;
import com.nemnous.bank.interfaces.InputReader;
import com.nemnous.bank.models.Account;
import com.nemnous.bank.models.Bank;
import com.nemnous.bank.models.Customer;
import com.nemnous.bank.models.Transaction;

import java.io.FileReader;
import java.io.IOException;

public class PropertyFileReader implements InputReader{
	
	private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final String INVALID_MSG = null;
	String path = "resources/config.properties";
	private final Bank bank = new Bank("SBI", "SBI000IFSC");
	
	public void read() {
		Properties property = null;
		try(FileReader reader = new FileReader(path);) {
			 property = new Properties();
		     property.load(reader);
		} catch (IOException e) {
		     logger.log(Level.WARNING, "File Not Found");
		     return;
		}

		//Getting all keys in a property file and
		//sorting because of orderly insertion for testing purpose.
		List<String> keys = new ArrayList<>(property.stringPropertyNames()); //contains all keys in properties file. 
		Collections.sort(keys);
		String input;
		String[] details;
		//iterating through all the keys for performing desired operation.
		for(String key : keys) {
			logger.log(Level.INFO, "\n{0}\n",property.getProperty(key));
			if (key.contains("addAccount")) {
				input = property.getProperty(key);
				details = input.split(" ");
				addAccount(details);
			} else if (key.contains("search")) {
				searchByAccount(property.getProperty(key));
			} else if (key.contains("deposit")) {
				input = property.getProperty(key);
				details = input.split(" ");
				deposit(details);
			} else if (key.contains("withdraw")) {
				input = property.getProperty(key);
				details = input.split(" ");
				withdraw(details);
			}
		}
	}

	/**
	 * this method is called when user requests
	 * to with draw money from a given account.
	 */
	public void withdraw(String[] details) {
		try {
			String account = details[0];
			String withdraw = details[1];
			float amount;
			if(withdraw.matches("[-+]?[0-9]*\\.?[0-9]+") ) {
				amount = Float.parseFloat(withdraw);
			} else {
				logger.log(Level.WARNING, "Expected a float Value");
				return;
			}
			Transaction transaction = new Transaction(account, amount);
			bank.withdrawFromAccount(transaction);
			logger.log(Level.INFO, "Withdrawn Succesfully");
		} catch (AccountNotFoundException
				| InvalidDetailsException
				| InsufficientBalanceException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (IndexOutOfBoundsException  e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
	}
	
	public void addAccount(String[] details) {
		try {
			String name = details[0];
			String phone = details[1];
			String address = details[2];
			String accountNumber = details[3];
			String type = details[4];
			Account account = new Account(
					new Customer(name, phone, address),
						accountNumber, type);
				bank.addAccount(account);
			logger.log(Level.INFO, "Account Added Succesfully");
		} catch (InvalidDetailsException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (IndexOutOfBoundsException  e) {
			logger.log(Level.WARNING, INVALID_MSG);
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
	
	public void deposit(String[] details) {
		try {
			String account = details[0];
			String deposit = details[1];			
			float amount;
			
			if(deposit.matches("[-+]?[0-9]*\\.?[0-9]+") ) {
				amount = Float.parseFloat(deposit);
			} else {
				logger.log(Level.WARNING, "Expected a float Value");
				return;
			}
			Transaction transaction = new Transaction(account, amount);
			bank.depositInAccount(transaction);
			logger.log(Level.INFO, "Succesfully Deposited");
		} catch (AccountNotFoundException | InvalidDetailsException e) {
				logger.log(Level.WARNING,e.getMessage());
		} catch (IndexOutOfBoundsException  e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
	}
}
