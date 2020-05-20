package br.com.pismo.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.pismo.accounts.exceptions.AccountAlreadyExistsException;
import br.com.pismo.accounts.exceptions.AccountNotFoundException;
import br.com.pismo.accounts.exceptions.AccountWithoutEnoughBalanceException;
import br.com.pismo.accounts.model.Account;
import br.com.pismo.accounts.service.AccountService;

@SpringBootTest
@ActiveProfiles("test")
class AccountsApplicationTests {

	private static final long DOCUMENT_NUMBER_ERROR = 1234567L;
	private static final long DOCUMENT_NUMBER = 123456789L;
	private static final long ACCOUNT_ID = 1L;
	private static final long ACCOUNT_ID_ERROR = 2L;
	private static final BigDecimal AVAILABLE_CREDIT_LIMIT = BigDecimal.valueOf(10.5);
	private static final BigDecimal CREDIT_VALUE_SUCCESS = BigDecimal.valueOf(9);
	private static final BigDecimal DEBIT_VALUE_SUCCESS = BigDecimal.valueOf(-9);
	
	private static final BigDecimal DEBIT_VALUE_ERROR = BigDecimal.valueOf(-11);
	
	
	@Autowired
    private AccountService accountService;
    
	@Test
	public void testSaveAndGetById() throws AccountNotFoundException {
		Account account = new Account(DOCUMENT_NUMBER, AVAILABLE_CREDIT_LIMIT);
		account = accountService.saveAccount(account);
		assertNotNull(account);
		assertNotNull(account.getAccountId());
		account = this.accountService.getAccountById(ACCOUNT_ID);
		assertNotNull(account);
		assertEquals(ACCOUNT_ID, account.getAccountId());
		assertEquals(DOCUMENT_NUMBER, account.getDocumentNumber());
		assertEquals(AVAILABLE_CREDIT_LIMIT, account.getAvailableCreditLimit());
	}
	
	@Test
	public void testFailGetById() {
		assertThrows(AccountNotFoundException.class, () -> this.accountService.getAccountById(ACCOUNT_ID_ERROR));
	}
	
	@Test
	public void testFailSave() {
		final Account account = new Account(DOCUMENT_NUMBER_ERROR, AVAILABLE_CREDIT_LIMIT);
		accountService.saveAccount(account);
		assertThrows(AccountAlreadyExistsException.class, () -> this.accountService.saveAccount(account));
	}
	
	@Test
	public void testCreditValueSuccess() {
		Account account = new Account(DOCUMENT_NUMBER + 2, AVAILABLE_CREDIT_LIMIT);
		account = accountService.saveAccount(account);
		assertNotNull(account);
		assertNotNull(account.getAccountId());
		account = this.accountService.getAccountById(account.getAccountId());
		
		account = this.accountService.creditAccount(CREDIT_VALUE_SUCCESS, account.getAccountId());
		
		assertEquals(AVAILABLE_CREDIT_LIMIT.add(CREDIT_VALUE_SUCCESS), account.getAvailableCreditLimit());
	}
	
	@Test
	public void testDebitValueSuccess() {
		Account account = new Account(DOCUMENT_NUMBER + 1, AVAILABLE_CREDIT_LIMIT);
		account = accountService.saveAccount(account);
		assertNotNull(account);
		assertNotNull(account.getAccountId());
		account = this.accountService.getAccountById(account.getAccountId());
		account = this.accountService.debitAccount(DEBIT_VALUE_SUCCESS, account.getAccountId());
		assertEquals(AVAILABLE_CREDIT_LIMIT.add(DEBIT_VALUE_SUCCESS), account.getAvailableCreditLimit());
	}
	
	@Test
	public void testDebitValueFail() {
		final Account account = accountService.saveAccount(new Account(DOCUMENT_NUMBER + 3, AVAILABLE_CREDIT_LIMIT));
		assertNotNull(account);
		assertNotNull(account.getAccountId());
		assertThrows(AccountWithoutEnoughBalanceException.class, () -> this.accountService.debitAccount(DEBIT_VALUE_ERROR, account.getAccountId()));
	}
}
