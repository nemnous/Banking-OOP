package com.nemnous.bank.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
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

public class FileInputReader implements InputReader{
	private final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	String path = "src/com/nemnous/bank/resources/input.txt";
	private final Bank bank = new Bank("SBI", "SBI000IFSC");
	private static final String INVALID_MSG = "Invalid query";
	private File file = new File(path);
	
	public void addAccount(String[] details) {
		try {
			String name = details[1];
			String phone = details[2];
			String address = details[3];
			String accountNumber = details[4];
			String type = details[5];
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
	
	public void displayAccounts() {
		for (Account account: bank.getAllAccounts()) {
			String details = account.toString();
			logger.log(Level.INFO, details);
		}
	}
	
	public void deposit(String[] details) {
		try {
			String account = details[1];
			String deposit = details[2];
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
	
	/**
	 * this method is called when user requests
	 * to with draw money from a given account.
	 */
	public void withdraw(String[] details) {
		

		try {
			String account = details[1];
			String withdraw = details[2];
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
	
	/**
	 * This function is called when a user requests
	 * to search the Accounts using an Account Number.
	 */
	public void searchByAccount(String[] details) {
		String account = details[1];
		try {
			logger.log(Level.INFO, "Account Details"
					+ bank.getAccountById(account));
		} catch (AccountNotFoundException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}
	
	
	/**
	 * This method is used to read input from the console.
	 * It gives the user the options available.
	 * @throws FileNotFoundException 
	 */
	public void read() {

		logger.log(Level.INFO,"------------------------------"
				+ "-----------------------------------------");

		logger.log(Level.INFO,"Welcome to  "
					+ bank.getName()
					+ "  " + bank.getIfsc());
		try(Scanner scan = new Scanner(file);) {
			while (scan.hasNextLine()) {
			      String line = scan.nextLine();
			      logger.log(Level.INFO, "\n\n {0}", line);
			      String[] input = line.split(" ");
			      switch (input[0]) {
					case "add":
						addAccount(input);
						continue;
					case "displayAll":
						displayAccounts();
						continue;
					case "search":
						searchByAccount(input);
						continue;
					case "deposit":
						deposit(input);
						continue;
					case "withdraw":
						withdraw(input);
						continue;
					default:
						continue;
					}
			}
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "File Not Found");
		}
			
	}


}
