package com.nemnous.bank.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nemnous.bank.exceptions.InvalidDetailsException;
import com.nemnous.bank.interfaces.InputReader;
import com.nemnous.bank.models.Account;
import com.nemnous.bank.models.Customer;
import com.nemnous.bank.models.Transaction;


public class DatabaseConnector implements InputReader {
	private final Logger logger;
	String path = "resources/input.txt";
	private File file;
	private static final String INVALID_MSG = "Invalid query";
	private static final String INVALID_SQL = "Invalid SQL Query";
	private static final int BRANCH_ID = 1;
	
	public Connection getActiveConnection() {

		try {
			return DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/banking","root", "test");
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Cant connect to Database");
		}
		return null;
	}
	
	
	public DatabaseConnector() {
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			file = new File(path);
		} catch (ClassNotFoundException e1) {
			logger.log(Level.WARNING, "Cant connect to Database");
		}
		
		
		
	}
	
	
	
	
	
	/**

	 * this parses the value and get details to create new account.
	 * @param input
	 */
	public void parseAddAccount(String[] input) {
		Connection connection = getActiveConnection();
		ResultSet result;
		if (connection == null) {
			return;
		}

		try(Statement statement = connection.createStatement()) {
			
			Customer customer = new Customer(input[1], input[2], input[3]);
			Account account = new Account(customer, input[4], input[5]);
			
			String customerQuery = "insert into customer (name,"
					+ " address, phone) values (\"" + customer.getName()
					+ "\"," +  "\"" + customer.getAddress() +  "\",\"" + customer.getPhone() +  "\"" + ");";
			
			statement.executeUpdate(customerQuery,
                    Statement.RETURN_GENERATED_KEYS);
			result = statement.getGeneratedKeys();
			result.first();
			long customerID = result.getLong(1);
			
			int accountTypeID = 0;

			if (account.getTypeOfAccount().equals("savings")) {
				accountTypeID = 1;
			} else if (account.getTypeOfAccount().equals("current")) {
				accountTypeID = 2;
			}else if(account.getTypeOfAccount().equals("fixed")) {
				accountTypeID = 3;
			} else if (account.getTypeOfAccount().equals("demat")) {
				accountTypeID = 4;
			}

			String accountQuery = "insert into account( account_number, branch_id, customer_id, account_type_id, balance)" + 
					"values(" + "\"" + account.getAccountNumber() + "\"" + "," +  BRANCH_ID + "," + customerID + "," +  accountTypeID+ "," + 0 + ");";
			
			statement.executeUpdate(accountQuery);

		} catch (IndexOutOfBoundsException  e) {
			logger.log(Level.WARNING, INVALID_MSG);
		} catch (SQLIntegrityConstraintViolationException e) {
			logger.log(Level.WARNING, "Constraint error probably duplicating primary key");
		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_SQL);
		} catch (InvalidDetailsException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}
	
	

	private void displayAccounts() {
		
		Connection connection = getActiveConnection();
		ResultSet result;
		if (connection == null) {
			return;
		}

		try(Statement statement = connection.createStatement()) {
			
			String displayQuery = "SELECT a.account_number, c.name, c.phone, c.address, a.balance"
					+ " FROM account as a, customer as c where c.id = a.customer_id;";
			statement.execute(displayQuery);
			result = statement.getResultSet();

			while (result.next()) {
				String output = result.getString("account_number") + " " + result.getString("name") + " "
						+ result.getString("phone") + " " + result.getString("address") + " " + result.getString("balance");
				logger.log(Level.INFO, output);
			}
			

		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_SQL);
		}
	}
	

	public void parseDeposit(String[] details) {
		
		Connection connection = getActiveConnection();
		ResultSet result;
		if (connection == null) {
			return;
		}

		try(Statement statement = connection.createStatement()) {
			String account = details[1];
			String withdraw = details[2];
			float amount = Float.parseFloat(withdraw);
			
			if (!searchByAccount(account)) {
				return;
			}
			new Transaction(account, amount);
			String balanceQuery = "select balance from account where account_number = \""+ account + "\";";
			statement.execute(balanceQuery);
			result = statement.getResultSet();
			float balance = 0;
			while (result.next()) {
				balance = result.getFloat("balance");
			}
			
			String updateQuery = "update account set balance =" + (balance + amount) + "where account_number = \"" + account +"\"";
			statement.execute(updateQuery);

			logger.log(Level.WARNING, "Amount Deposited");			

		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_SQL);
		} catch (InvalidDetailsException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			logger.log(Level.WARNING, "Invalid Input");
		}
		
	}


	public void parseWithdraw(String[] details) {
		
		Connection connection = getActiveConnection();
		ResultSet result;
		if (connection == null) {
			return;
		}

		try(Statement statement = connection.createStatement()) {
			String account = details[1];
			String withdraw = details[2];
			float amount = Float.parseFloat(withdraw);
			
			if (!searchByAccount(account)) {
				return;
			}
			new Transaction(account, amount);
			String balanceQuery = "select balance from account where account_number = \""+ account + "\";";
			statement.execute(balanceQuery);
			result = statement.getResultSet();
			float balance = 0;
			while (result.next()) {
				balance = result.getFloat("balance");
			}
			
			String updateQuery = "update account set balance =" + (balance - amount) + "where account_number = \"" + account +"\"";
			statement.execute(updateQuery);

			logger.log(Level.WARNING, "Withdrawn Succesfully");			

		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_SQL);
		} catch (InvalidDetailsException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			logger.log(Level.WARNING, "Invalid Input");
		}
		
	}


	
	private boolean searchByAccount(String accountNumber) {
		Connection connection = getActiveConnection();
		ResultSet result;
		
		if (connection == null) {
			return false;
		}

		try(Statement statement = connection.createStatement()) {

			String searchQuery = "SELECT a.account_number, c.name, c.phone, c.address, a.balance"
					+ " FROM account as a, customer as c where c.id = a.customer_id "
					+ "and a.account_number = \""+ accountNumber + "\";";
			statement.execute(searchQuery);
			result = statement.getResultSet();

			int count = 0;
			while (result.next()) {
				++count;
				String output = result.getString("account_number") + " " + result.getString("name") + " "
						+ result.getString("phone") + " " + result.getString("address") + " " + result.getString("balance");
				logger.log(Level.INFO, output);
			}
			
			if (count == 0) {
//				throw new AccountNotFoundException("Account doesnt Exist")
				logger.log(Level.WARNING, "Account Not Found");
				return false;
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, INVALID_SQL);
		}
		return true;
	}

	/**
	 * This method is used to read input from the file.
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
						displayAccounts();
						continue;
					case "search":
						searchByAccount(input[1]);
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
	