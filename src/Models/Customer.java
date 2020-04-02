package Models;

public class Customer {
	private String name;
	private String phoneNumber;
	private String addressString;
	
	
	
	public Customer(String name, String phoneNumber, String addressString) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.addressString = addressString;
	}

	@Override
	public String toString() {
		return "name=" + name + ", phoneNumber=" + phoneNumber + ", addressString=" + addressString;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAddressString() {
		return addressString;
	}
	
	public void setAddressString(String addressString) {
		this.addressString = addressString;
	}
	
	
}
