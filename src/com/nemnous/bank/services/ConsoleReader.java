package com.nemnous.bank.services;
import java.util.Random;
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

/**
 * In this class the input from the console is read.
 * @author nemnous.
 *
 */
public class ConsoleReader implements InputReader{
	private final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final Bank sbiBank = new Bank("SBI", "SBI000IFSC");
	Scanner scan = new Scanner(System.in);
	Random random = new Random();

	/**
	 * @return a string of account number
	 */
	public String generateAccountNumber() {
		final int twelve = 12;
		int length = twelve;
	    char[] digits = new char[length];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return new String(digits);
	}
	/**
	 * this function is called when a user
	 * selects to add account to bank.
	 * This reads required account details from user.
	 */
	public void addNewAccount() {
		logger.log(Level.INFO, "Enter Name");
		String nameString = scan.nextLine();
		logger.log(Level.INFO,"Enter Phone Number");
		String phoneString = scan.nextLine();
		logger.log(Level.INFO, "Enter Address");
		String addressString = scan.nextLine();
		logger.log(Level.INFO, "Type of Account \n1. Savings\n"
				+ "2. Fixed Account");
		String temp = scan.nextLine();
		int t = 0;
		try {
			t = Integer.parseInt(temp);
		} catch (NumberFormatException e) {
			logger.log(Level.WARNING, "Invalid Input Default value set to"
					+ "SAVING");
		}
		String typeString;
		if (t == 2) {
			typeString = "Fixed Deposit";
		} else {
			typeString = "Savings";
		}
		try {
			Account account = new Account(
				new Customer(nameString, phoneString, addressString),
					generateAccountNumber(), typeString);
			sbiBank.addAccount(account);
			String tempString = account.toString();
			logger.log(Level.INFO, tempString);
		} catch (InvalidDetailsException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}
	/**
	 * This function is called when a user requests
	 * to display all the accounts data.
	 */
	public void displayAccounts() {
		for (Account i: sbiBank.getAllAccounts()) {
			String accString = i.toString();
			logger.log(Level.INFO, accString);
		}
	}
	/**
	 * This function is called when a user requests
	 * to search the Accounts using an Account Number.
	 */
	public void searchByAccount() {
		logger.log(Level.INFO, "Enter Account Number to Search:\n");
		String temp = scan.nextLine();
		try {
			logger.log(Level.INFO, "Account Details"
					+ sbiBank.getAccountById(temp));
		} catch (AccountNotFoundException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}

	/**
	 * This function is called when user requests
	 * to deposit amount to a specific bank account.
	 */
	public void deposit() {
		logger.log(Level.INFO, "Enter Account Number");
		String acc = scan.nextLine();
		logger.log(Level.INFO, "Enter Amount");
		String tempString = scan.nextLine();
		float amt;
		try {
			amt = Float.parseFloat(tempString);
		} catch (NumberFormatException e) {
			logger.log(Level.WARNING, "Expected a float Value");
			return;
		}
		try {
			Transaction transaction = new Transaction(acc, amt);
			sbiBank.depositInAccount(transaction);
			logger.log(Level.INFO, "Succesfully Deposited");
		} catch (AccountNotFoundException | InvalidDetailsException e) {
				logger.log(Level.WARNING,e.getMessage());
		}
	}
	/**
	 * this method is called when user requests
	 * to with draw money from a given account.
	 */
	public void withdraw() {
		logger.log(Level.INFO, "Enter Account Number");
		String acc1 = scan.nextLine();
		logger.log(Level.INFO, "Enter Amount");
		String tempString = scan.nextLine();
		float amt1;
		try {
			amt1 = Float.parseFloat(tempString);
		} catch (NumberFormatException e) {
			logger.log(Level.WARNING, "Expected a float Value");
			return;
		}
		try {
			Transaction transaction = new Transaction(acc1, amt1);
			sbiBank.withdrawFromAccount(transaction);
			logger.log(Level.INFO, "Withdrawn Succesfully");
		} catch (AccountNotFoundException
				| InvalidDetailsException
				| InsufficientBalanceException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}

	/**
	 * This method is used to read input from the console.
	 * It gives the user the options available.
	 */
	public void read() {

		logger.log(Level.INFO,"------------------------------"
				+ "-----------------------------------------");

		logger.log(Level.INFO,"Welcome to  "
					+ sbiBank.getName()
					+ "  " + sbiBank.getIfsc());

		int key = 0;
		do {
			logger.log(Level.INFO,"Select operation to perform \n"
					+ "1. Add New Account \n"
					+ "2. Display All Accounts \n"
					+ "3. Search By Account \n"
					+ "4. Deposit Money in Account\n"
					+ "5. Withdraw Money From Account\n"
					+ "6. Exit\n");

			try {
				String t = scan.nextLine();
				if (t.equals("")) {
					continue;
				}
				key = Integer.parseInt(t);
			} catch (NumberFormatException e) {
				logger.log(Level.INFO, "Enter a number");
			}

			switch (key) {
			case 1:
				addNewAccount();
				break;
			case 2:
				displayAccounts();
				break;
			case 3:
				searchByAccount();
				break;
			case 4:
				deposit();
				break;
			case 5:
				withdraw();
				break;
			default:
				continue;
			}
		} while (key != 6);
	}

}
