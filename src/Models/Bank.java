package Models;

import java.util.ArrayList;
import java.util.List;

import customExceptions.InsufficientBalanceException;
import customExceptions.AccountNotFoundException;

import AbstractClassModel.AbstractBank;

public class Bank extends AbstractBank{
	private String name;
	private String IFSC;
	private List<Account> accounts;
	
	
	
	public Bank(String name, String iFSC) {
		super();
		this.name = name;
		IFSC = iFSC;
		this.accounts = new ArrayList<Account>();
	}

	public Account getAccountById(String id) {
		for(Account acc : this.accounts) {
			if(acc.getAccountNumber().equals(id)) {
				return acc;
			}
		}
		throw new AccountNotFoundException("Account Doesnt Exist");
		
	}
	
	public void depositInAccount(String id, float amount) {
		Account tempAccount = null;
		for(Account account : this.accounts) {
			if (account.getAccountNumber().equals(id)) {
				tempAccount = account;
			}
		}
		if (tempAccount == null) {
			//Should raise Exception Invalid Account
			throw new AccountNotFoundException("Account doesnt Exist");
		}
		
		tempAccount.deposit(amount);
		
	}
	
	public void withdrawFromAccount(String id, float amount) throws AccountNotFoundException, InsufficientBalanceException{
		Account tempAccount = null;
		for(Account account : this.accounts) {
			if (account.getAccountNumber().equals(id)) {
				tempAccount = account;
			}
		}
		if (tempAccount == null) {
			//Should raise Exception Invalid Account
			throw new AccountNotFoundException("Account Doesnt Exist");
			
		}
		
		if (!tempAccount.withdraw(amount)) {
			//Should raise Insufficient Balance Account
			throw new InsufficientBalanceException("Insufficient Balance");
			
		}
		
	}
	
	
	
	public void addAccount(Account acc) {
		this.accounts.add(acc);
	}
	
	public List<Account> getAllAccounts() {
		return accounts;
	}
	
	public boolean removeAccount(String id) {
		Account tempAccount = null;
		for(Account account : this.accounts) {
			if (account.getAccountNumber().equals(id)) {
				tempAccount = account;
				break;
			}
		}
		if (tempAccount != null) {
			return accounts.remove(tempAccount);
		}
		
		return false;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIFSC() {
		return IFSC;
	}

	public void setIFSC(String iFSC) {
		IFSC = iFSC;
	}
	
	

}
