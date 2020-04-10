package com.nemnous.bank.exceptions;


/**
 * This is a custom exception raised when the
 * user requests the account which doesn't exist.
 * @author nemnous
 *
 */
@SuppressWarnings("serial")
public class AccountNotFoundException extends RuntimeException {

    private final String message;

    /**
     * Constructor with message as parameter.
     * @param msg
     */
    public AccountNotFoundException(final String message) {
        this.message = message;
    }


    /**
     * Overloaded constructor with a Throwable cause passed to
     * the super constructor and message.
     * @param cause
     * @param msg
     */
    public AccountNotFoundException(final Throwable cause, final String message) {
        super(cause);
        this.message = message;
    }

    /**
     * getter function for the private variable message.
     * @return message
     */
    @Override
    public String getMessage() {
        return message;
    }

}