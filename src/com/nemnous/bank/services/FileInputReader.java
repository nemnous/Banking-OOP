package com.nemnous.bank.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nemnous.bank.interfaces.InputReader;
import com.nemnous.bank.models.Details;
import com.nemnous.bank.models.Transaction;

public class FileInputReader implements InputReader{
	private final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	String path = "resources/input.txt";
	BankManager bankManager = new BankManager();

	private static final String INVALID_MSG = "Invalid query";
	private File file = new File(path);
	
	/**

	 * this parses the value and get details to create new account.
	 * @param input
	 */
	public void parseAddAccount(String[] input) {
		Details details = new Details();
		try {
			details.setName(input[1]);
			details.setPhone(input[2]);
			details.setAddress(input[3]);
			details.setAccountNumber(input[4]);
			details.setType(input[5]);

			bankManager.addToBank(details);

		} catch (IndexOutOfBoundsException  e) {
			logger.log(Level.WARNING, INVALID_MSG);
		}
	}
	
	
	
	public void parseDeposit(String[] details) {
		try {
			String account = details[1];
			String withdraw = details[2];
			float amount = Float.parseFloat(withdraw);
			Transaction transaction = new Transaction(account, amount);
			
			bankManager.depositInBank(transaction);

		} catch (IndexOutOfBoundsException  e) {
			logger.log(Level.WARNING, INVALID_MSG);
		} catch (NumberFormatException e) {
			logger.log(Level.WARNING, "Expected a float Value");
		}
	}


	/**
	 * this method is called when user requests
	 * to with draw money from a given account.
	 */
	public void parseWithdraw(String[] details) {
		try {
			String account = details[1];
			String withdraw = details[2];
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
	 * This method is used to read input from the console.
	 * It gives the user the options available.
	 * @throws FileNotFoundException 
	 */
	public void read() {

		logger.log(Level.INFO,"------------------------------"
				+ "-----------------------------------------");

		try(Scanner scan = new Scanner(file);) {
			while (scan.hasNextLine()) {
			      String line = scan.nextLine();
			      logger.log(Level.INFO, "\n\n {0}", line);
			      String[] input = line.split(" ");
			      switch (input[0]) {
					case "add":
						parseAddAccount(input);
						continue;
					case "displayAll":
						bankManager.displayAccounts();
						continue;
					case "search":
						bankManager.searchByAccount(input[1]);
						continue;
					case "deposit":
						parseDeposit(input);
						continue;
					case "withdraw":
						parseWithdraw(input);
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
