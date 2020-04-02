package Main;

import java.util.Random;
import java.util.Scanner;

import Models.Account;
import Models.Bank;
import Models.Customer;

public class Main {
	
	public static String generateRandom() {
		int length = 12;
	    Random random = new Random();
	    char[] digits = new char[length];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return new String(digits);
	}

	public static void main(String[] args) {
		
		Bank sbiBank = new Bank("SBI", "SBI000IFSC");
		
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("Welcome to  " + sbiBank.getName() + "  " + sbiBank.getIFSC());
		
		int key = 0; 
		do {
			Scanner scan = new Scanner(System.in);
			System.out.println("Select operation to perform \n1. Add New Account \n"
					+ "2. Display All Accounts \n3. Search By Account \n4. Deposit Money in Account\n"
					+ "5. Withdraw Money From Account\n6. Exit\n");
			key = Integer.parseInt(scan.nextLine());
			switch (key) {
			case 1:
				System.out.println("Enter Name");
				String nameString =scan.nextLine();
				System.out.println("Enter Phone Number");
				String phoneString = scan.nextLine();
				System.out.println("Enter Address");
				String addressString = scan.nextLine();
				System.out.println("Type of Account \n1. Savings\n2. Fixed Account");
				int t = scan.nextInt();
				String typeString;
				if(t== 2)
					typeString = "Fixed Deposit";
				else {
					typeString = "Savings";
				}
				Account account = new Account(new Customer(nameString, phoneString, addressString), generateRandom(), typeString);
				System.out.println(account);
				sbiBank.addAccount(account);
				break;
			case 2:
				for(Account i: sbiBank.getAllAccounts()) {
					System.out.println(i);
				}
				break;
			case 3:
				System.out.println("Enter Account Number to Search:\n");
				String temp = scan.nextLine();
				System.out.println(sbiBank.getAccountById(temp));
				break;
			case 4:
				System.out.println("Enter Account Number");
				String acc = scan.nextLine();
				System.out.println("Enter Amount");
				float amt = scan.nextFloat();
				sbiBank.depositInAccount(acc, amt);
				break;
			case 5:
				System.out.println("Enter Account Number");
				String acc1 = scan.nextLine();
				System.out.println("Enter Amount");
				float amt1 = scan.nextFloat();
				sbiBank.withdrawFromAccount(acc1, amt1);
				break;
				
			default:
				continue;
			}
		
		}while(key!= 6);
		
	}

}
