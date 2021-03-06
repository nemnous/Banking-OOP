package com.nemnous.bank.interfaces;

import java.util.List;

import com.nemnous.bank.models.Account;
import com.nemnous.bank.models.Transaction;


/**
 * This is the interface for the bank
 * model.
 * @author nemnous
 *
 */
public interface Bankable {
	/**
	 * Adds Account to the bank.
	 * @param a the Account object.
	 */
	void addAccount(Account a);



	/**
	 * returns account with specific account number.
	 * @param id the account number.
	 * @return Account object with given account number.
	 */
	Account getAccountById(String id);


	/**
	 * deposits money in a specific bank account.
	 * @param id the given account number and the amount.
	 * @param amount amount to withdraw
	 */
	void depositInAccount(Transaction t);

	/**
	 * withdraws money from the given account number.
	 * @param id the account number.
	 * @param amount the amount to withdraw.
	 */
	void withdrawFromAccount(Transaction t);
	/**
	 * @return List of all the available bank accounts.
	 */
	List<Account> getAllAccounts();

}
