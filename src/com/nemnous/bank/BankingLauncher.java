package com.nemnous.bank;

import com.nemnous.bank.interfaces.InputReader;
import com.nemnous.bank.models.ConsoleReader;

/**
 * Driver class for the Application.
 * @author nemnous
 *
 */
public class BankingLauncher {
	/**
	 * Start of the program.
	 * @param args
	 */
	public static void main(String[] args) {
		
		InputReader inputReader = new ConsoleReader();
		inputReader.read();
	}

}
