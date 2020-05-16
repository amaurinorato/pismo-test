package br.com.pismo.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccountAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountAlreadyExistsException() {
		super();
	}
	public AccountAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
	public AccountAlreadyExistsException(String message) {
		super(message);
	}
	public AccountAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
