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
	private String phone;
	private String address;

	/**
	 * Parameterised Constructor.
	 * @param name - name of the customer.
	 * @param phone - phone number of the customer.
	 * @param address - Address of the customer.
	 * @throws Invalid details exception if any of the arguments are null
	 * if the name contains numbers and special characters.
	 */
	public Customer(final String name, final String phone,
			final String address) {
		if (name == null || phone == null || address == null
				|| name.length() == 0|| phone.length() == 0|| address.length() == 0) {
			throw new InvalidDetailsException("Details Cannot be Empty");
		}
		if (Pattern.compile("[0-9]").matcher(name).find()) {
			throw new InvalidDetailsException("Name cannot contain numbers");
		}

		Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		Matcher m = p.matcher(phone);
		boolean checkPhone =  (m.find() && m.group().equals(phone));
		if (!checkPhone) {
			throw new InvalidDetailsException("Invalid Phone Number");
		}

		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	/**
	 * to String method for the class.
	 */
	@Override
	public String toString() {
		return "name=" + name + ", phone=" + phone + ", address=" + address;
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
	 * returns the private variable phone.
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * sets the private variable phone.
	 * @param phone
	 */
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	/**
	 * returns the private variable address.
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * sets the private variable address.
	 * @param address
	 */
	public void setAddress(final String address) {
		this.address = address;
	}
}
