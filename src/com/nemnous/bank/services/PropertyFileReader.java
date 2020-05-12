package com.nemnous.bank.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nemnous.bank.interfaces.InputReader;
import com.nemnous.bank.models.Details;
import com.nemnous.bank.models.Transaction;

import java.io.FileReader;
import java.io.IOException;

public class PropertyFileReader implements InputReader{
	
	private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private static final String INVALID_MSG = "Index out of bounds";
	
	String path = "resources/config.properties";

	/**
	 * Implemented Composition rather than Inheritance.
	 */
	BankManager bankManager = new BankManager();
	
	/**
	 * implementing the read method. This reads the file from the path
	 * and loads the properties.
	 */
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
				parseAddAccount(details);
			} else if (key.contains("search")) {
				bankManager.searchByAccount(property.getProperty(key));
			} else if (key.contains("deposit")) {
				input = property.getProperty(key);
				details = input.split(" ");
				parseDeposit(details);
			} else if (key.contains("withdraw")) {
				input = property.getProperty(key);
				details = input.split(" ");
				parseWithdraw(details);
			}
		}
	}

	/**
	 * this method is called when the key has withdraw
	 * this parses the value and get details to make transaction.
	 */
	public void parseWithdraw(String[] details) {
		try {
			String account = details[0];
			String withdraw = details[1];
			float amount = Float.parseFloat(withdraw);
			Transaction transaction = new Transaction(account, amount);
			bankManager.withdrawFromBank(transaction);
		} catch (IndexOutOfBoundsException  e) {
			logger.log(Level.WARNING, INVALID_MSG);
		} catch (NumberFormatException e) {
			logger.log(Level.WARNING, "Expected a float Value");
		}
	}
	
	
	/**
	 * this method is called when the key has add account
	 * this parses the value and get details to create new account.
	 */
	public void parseAddAccount(String[] input) {
		Details details = new Details();
		try {
			details.setName(input[0]);
			details.setPhone(input[1]);
			details.setAddress(input[2]);
			details.setAccountNumber(input[3]);
			details.setType(input[4]);
			
			bankManager.addToBank(details);

		} catch (IndexOutOfBoundsException  e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
	}
	
	/**
	 * this method is called when the key has withdraw
	 * this parses the value and get details to make transaction.
	 */
	public void parseDeposit(String[] details) {
		try {
			String account = details[0];
			String withdraw = details[1];
			float amount = Float.parseFloat(withdraw);
			Transaction transaction = new Transaction(account, amount);
			
			bankManager.depositInBank(transaction);

		} catch (IndexOutOfBoundsException  e) {
			logger.log(Level.WARNING, INVALID_MSG);
		} catch (NumberFormatException e) {
			logger.log(Level.WARNING, "Expected a float Value");
		}
	}
}
