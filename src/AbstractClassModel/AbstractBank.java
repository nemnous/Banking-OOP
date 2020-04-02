package AbstractClassModel;

import java.util.List;

import Models.Account;

public abstract class AbstractBank {
	public abstract void addAccount(Account a);
	public abstract boolean removeAccount(String id);
	public abstract Account getAccountById(String id);
	public abstract void depositInAccount(String id, float amount);
	public abstract void withdrawFromAccount(String id, float amount);
	public abstract List<Account> getAllAccounts();
}
