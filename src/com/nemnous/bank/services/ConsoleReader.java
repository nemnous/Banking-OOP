package com.nemnous.bank.services;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nemnous.bank.interfaces.InputReader;
import com.nemnous.bank.models.Details;
import com.nemnous.bank.models.Transaction;

/**
 * In this class the input from the console is read.
 * @author nemnous.e
 *
 */
public class ConsoleReader implements InputReader{
	private final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	BankManager bankManager = new BankManager();
	Scanner scan = new Scanner(System.in);
	Random random = new Random();

	/**
	 * @return a string of account number
	 */
	public String generateAccountNumber() {
		final int length = 12;
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
	public void readAddAccount() {
		Details details = new Details();
		logger.log(Level.INFO, "Enter Name");
		details.setName(scan.nextLine());
		logger.log(Level.INFO, "Enter Phone Number");
		details.setPhone(scan.nextLine());
		logger.log(Level.INFO, "Enter Address");
		details.setAddress(scan.nextLine());
		logger.log(Level.INFO, "Type of Account \n1. Savings\n"
				+ "2. Fixed Account");
		details.setAccountNumber(generateAccountNumber());
		String choice = scan.nextLine();
		int option = 0;
		try {
			option = Integer.parseInt(choice);
		} catch(NumberFormatException e) {
			logger.log(Level.WARNING, "Invalid Input Default value set to"
					+ " SAVING");
		}

		if (option == 2) {
			details.setType("Fixed Deposit");
		} else {
			details.setType("Savings");
		}

		bankManager.addToBank(details);
	}



	/**
	 * This function is called when user requests
	 * to deposit amount to a specific bank account.
	 */
	public void readDeposit() {
		logger.log(Level.INFO, "Enter Account Number");
		String account = scan.nextLine();
		logger.log(Level.INFO, "Enter Amount");
		String deposit = scan.nextLine();
		try {
			float amount = Float.parseFloat(deposit);
			Transaction transaction = new Transaction(account, amount);
			bankManager.depositInBank(transaction);
		} catch(NumberFormatException e) {
			logger.log(Level.WARNING, "Enter float value only");
		}
	}

	/**
	 * this method is called when user requests
	 * to with draw money from a given account.
	 */
	public void readWithdraw() {
		logger.log(Level.INFO, "Enter Account Number");
		String account = scan.nextLine();
		logger.log(Level.INFO, "Enter Amount");
		String withdraw = scan.nextLine();

		try {
			float amount = Float.parseFloat(withdraw);
			Transaction transaction = new Transaction(account, amount);
			bankManager.withdrawFromBank(transaction);
		} catch(NumberFormatException e) {
			logger.log(Level.WARNING, "Enter float value only");
		}
	}


	/**
	 * This method is used to read input from the console.
	 * It gives the user the options available.
	 */
	public void read() {

		logger.log(Level.INFO,"------------------------------"
				+ "-----------------------------------------");
		int key = 0;

		do {
			logger.log(Level.INFO,"Select operation to perform \n"
					+ "1. Add New Account \n"
					+ "2. Display All Accounts \n"
					+ "3. Search By Account \n"
					+ "4. Deposit Money in Account\n"
					+ "5. Withdraw Money From Account\n"
					+ "6. Exit\n");

			String choice = scan.nextLine();
			if(!choice.equals("") && choice.matches("\\d+") && choice.length() == 1) {
				key = Integer.parseInt(choice);
			} else {
				logger.log(Level.INFO, "Enter a valid number");
				continue;
			}

			switch (key) {
			case 1:
				readAddAccount();
				break;
			case 2:
				bankManager.displayAccounts();
				break;
			case 3:
				logger.log(Level.INFO, "Enter account Number");
				bankManager.searchByAccount(scan.nextLine());
				break;
			case 4:
				readDeposit();
				break;
			case 5:
				readWithdraw();
				break;
			default:
				continue;
			}
		} while (key != 6);
	}

}
