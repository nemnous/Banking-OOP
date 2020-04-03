package com.nemnous.bank.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nemnous.bank.exceptions.InvalidDetailsException;

/**
 * This is the Customer class which contains the
 * details of the customer which includes name,
 * phone number and address.
 * @author nemnous
 *
 */
public class Customer {
	private String name;
	private String phoneNumber;
	private String addressString;

	/**
	 * Parameterized Constructor.
	 * @param name - name of the customer.
	 * @param phoneNumber - phone number of the customer.
	 * @param addressString - Address of the customer.
	 * @throws Invalid details exception if any of the arguments are null
	 * if the name contains numbers and special characters.
	 */
	public Customer(final String name, final String phoneNumber,
			final String addressString) throws InvalidDetailsException {
		if (name == null || phoneNumber == null || addressString == null
				|| name.length() == 0|| phoneNumber.length() == 0|| addressString.length() == 0) {
			throw new InvalidDetailsException("Details Cannot be Empty");
		}
		if (Pattern.compile("[0-9]").matcher(name).find()) {
			throw new InvalidDetailsException("Name cannot contain numbers");
		}

		Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		Matcher m = p.matcher(phoneNumber);
		boolean checkPhone =  (m.find() && m.group().equals(phoneNumber));
		if (!checkPhone) {
			throw new InvalidDetailsException("Invalid Phone Number");
		}

		this.name = name;
		this.phoneNumber = phoneNumber;
		this.addressString = addressString;
	}

	/**
	 * to String method for the class.
	 */
	@Override
	public String toString() {
		return "name=" + name + ", phoneNumber=" + phoneNumber + ", addressString=" + addressString;
	}

	/**
	 * returns the private variable name.
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the private variable name.
	 * @param name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * returns the private variable phoneNumber.
	 * @return phone
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * sets the private variable phoneNumber.
	 * @param phoneNumber
	 */
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * returns the private variable addressString.
	 * @return address
	 */
	public String getAddressString() {
		return addressString;
	}

	/**
	 * sets the private variable addressString.
	 * @param addressString
	 */
	public void setAddressString(final String addressString) {
		this.addressString = addressString;
	}
}
