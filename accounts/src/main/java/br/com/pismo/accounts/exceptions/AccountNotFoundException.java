package br.com.pismo.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccountNotFoundException() {
        super();
    }
    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public AccountNotFoundException(String message) {
        super(message);
    }
    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }

	
	
}
