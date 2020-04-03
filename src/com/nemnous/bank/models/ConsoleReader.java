package com.nemnous.bank.models;
import java.util.Random;
import java.util.Scanner;

import com.nemnous.bank.interfaces.InputReader;

/**
 * In this class the input from the console is read.
 * @author HP
 *
 */
public class ConsoleReader implements InputReader{

	/**
	 * Generates an Account number of 12 digits.
	 * @return a string of account number
	 */
	public String generateAccountNumber() {
		final int twelve = 12;
		int length = twelve;
	    Random random = new Random();
	    char[] digits = new char[length];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return new String(digits);
	}


	/**
	 * This method is used to read input from the console.
	 */
	public void read() {
		Bank sbiBank = new Bank("SBI", "SBI000IFSC");

		System.out.println("------------------------------"
				+ "-----------------------------------------");

		System.out.println("Welcome to  "
					+ sbiBank.getName()
					+ "  " + sbiBank.getIfsc());

		int key = 0;
		do {
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.println("Select operation to perform \n"
					+ "1. Add New Account \n"
					+ "2. Display All Accounts \n"
					+ "3. Search By Account \n"
					+ "4. Deposit Money in Account\n"
					+ "5. Withdraw Money From Account\n"
					+ "6. Exit\n");

			try {
				key = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("Enter numerical option");
			}

			switch (key) {
			case 1:
				System.out.println("Enter Name");
				String nameString = scan.nextLine();
				System.out.println("Enter Phone Number");
				String phoneString = scan.nextLine();
				System.out.println("Enter Address");
				String addressString = scan.nextLine();
				System.out.println("Type of Account \n1. Savings\n"
						+ "2. Fixed Account");
				int t = scan.nextInt();
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
					System.out.println(account);
					sbiBank.addAccount(account);
				} catch (Exception e) {
					System.out.println(e);
				}

				break;
			case 2:
				for (Account i: sbiBank.getAllAccounts()) {
					System.out.println(i);
				}
				break;
			case 3:
				System.out.println("Enter Account Number to Search:\n");
				String temp = scan.nextLine();
				try {
					System.out.println("Account Details" + 
				sbiBank.getAccountById(temp));
				} catch (Exception e) {
					System.out.println(e);
				}
				break;
			case 4:
				System.out.println("Enter Account Number");
				String acc = scan.nextLine();
				System.out.println("Enter Amount");
				float amt = scan.nextFloat();
				try {
				sbiBank.depositInAccount(acc, amt);
				System.out.println("Succesfully Deposited");
				} catch (Exception e) {
					System.out.println(e);
				}
				break;
			case 5:
				System.out.println("Enter Account Number");
				String acc1 = scan.nextLine();
				System.out.println("Enter Amount");
				float amt1 = scan.nextFloat();
				try {
					sbiBank.withdrawFromAccount(acc1, amt1);
					System.out.println("Withdrawn Succesfully");
				} catch (Exception e) {
					System.out.println(e);
				}
				break;

			default:
				continue;
			}

		} while (key != 6);

	}

}
