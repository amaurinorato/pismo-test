package br.com.pismo.accounts.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.pismo.accounts.exceptions.AccountAlreadyExistsException;
import br.com.pismo.accounts.exceptions.AccountNotFoundException;
import br.com.pismo.accounts.exceptions.AccountWithoutEnoughBalanceException;
import br.com.pismo.accounts.model.ErrorResponse;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error", details);
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AccountNotFoundException.class)
	public final ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, "Account not found", details);
		return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AccountAlreadyExistsException.class)
	public final ResponseEntity<Object> handleAccountAlreadyExists(AccountAlreadyExistsException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, "Account already exists", details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccountWithoutEnoughBalanceException.class)
	public final ResponseEntity<Object> handleAccountWithoutBalance(AccountWithoutEnoughBalanceException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, "Account doesn't have enough balance to make the transaction", details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, 
			HttpHeaders headers, 
			HttpStatus status, 
			WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ErrorResponse apiError = 
				new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(
				ex, apiError, headers, apiError.getStatus(), request);
	}
	
	 @Override
	    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	        Throwable mostSpecificCause = ex.getMostSpecificCause();
	        ErrorResponse errorResponse;
	        if (mostSpecificCause != null) {
	            String message = mostSpecificCause.getMessage();
	            errorResponse = new ErrorResponse(status, message, null);
	        } else {
	            errorResponse = new ErrorResponse(status, ex.getMessage(), null );
	        }
	        return new ResponseEntity(errorResponse, headers, status);
	    }
}