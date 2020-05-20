package br.com.pismo.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccountWithoutEnoughBalanceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountWithoutEnoughBalanceException() {
		super();
	}
	public AccountWithoutEnoughBalanceException(String message, Throwable cause) {
		super(message, cause);
	}
	public AccountWithoutEnoughBalanceException(String message) {
		super(message);
	}
	public AccountWithoutEnoughBalanceException(Throwable cause) {
		super(cause);
	}

}