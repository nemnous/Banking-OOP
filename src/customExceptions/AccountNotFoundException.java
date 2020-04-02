package customExceptions;


@SuppressWarnings("serial")
public class AccountNotFoundException extends RuntimeException {
	 
    private String message;
 
    public AccountNotFoundException(String message) {
        this.message = message;
    }
 
    public AccountNotFoundException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }
 
    public String getMessage() {
        return message;
    }
 
}