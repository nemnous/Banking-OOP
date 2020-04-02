package AbstractClassModel;

public abstract class AbstractAccount {
	public abstract void deposit(float cash);
	public abstract boolean withdraw(float cash);
	public abstract float getAvailableBalance();
}
