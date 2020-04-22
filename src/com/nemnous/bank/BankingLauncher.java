package com.nemnous.bank;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nemnous.bank.interfaces.InputReader;
import com.nemnous.bank.services.ConsoleReader;
import com.nemnous.bank.services.FileReader;

/**
 * Driver class for the Application.
 * @author nemnous
 *
 */
public class BankingLauncher {
	private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	static Scanner scan = new Scanner(System.in);
	/**
	 * Start of the program.
	 * @param args
	 */
	public static void main(String[] args) {
		logger.log(Level.FINE, "----------------------------------------------------");
		logger.log(Level.INFO, "Press 1 to read from Console\n"
				+ "Press 2 to read from File");
		switch (scan.nextInt()) {
			case 1:
				InputReader consoleReader = new ConsoleReader();
				consoleReader.read();
				break;
			case 2:
				InputReader fileReader = new FileReader();
				fileReader.read();
				break;
			default:
				break;
		}
		InputReader inputReader = new FileReader();
		inputReader.read();
	}

}
