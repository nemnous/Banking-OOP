package com.nemnous.bank.interfaces;

/**
 * This is interface for the Account class.
 * @author nemnous
 *
 */
public interface Accountable {
    /**
	 * money to be deposited in account.
	 * @param cash
	 */
	void deposit(float cash);

	/**
	 * money to be withdrawn from account.
	 * @param cash
	 * @return true if the withdraw is successful else false.
	 */
	boolean withdraw(float cash);

	/**
	 * @return returns the available balance.
	 */
	float getAvailableBalance();
}
