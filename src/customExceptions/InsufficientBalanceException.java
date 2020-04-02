package customExceptions;

@SuppressWarnings("serial")
public class InsufficientBalanceException extends RuntimeException {
	 
    private String message;
 
    public InsufficientBalanceException(String message) {
        this.message = message;
    }
 
    public InsufficientBalanceException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }
 
    public String getMessage() {
        return message;
    }
 
}