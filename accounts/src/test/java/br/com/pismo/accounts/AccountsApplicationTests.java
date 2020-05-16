package br.com.pismo.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.pismo.accounts.exceptions.AccountAlreadyExistsException;
import br.com.pismo.accounts.exceptions.AccountNotFoundException;
import br.com.pismo.accounts.model.Account;
import br.com.pismo.accounts.service.AccountService;

@SpringBootTest
@ActiveProfiles("test")
class AccountsApplicationTests {

	private static final long DOCUMENT_NUMBER_ERROR = 1234567L;
	private static final long DOCUMENT_NUMBER = 123456789L;
	private static final long ACCOUNT_ID = 1L;
	private static final long ACCOUNT_ID_ERROR = 2L;

	@Autowired
    private AccountService accountService;
    
	@Test
	public void testSaveAndGetById() throws AccountNotFoundException {
		Account account = new Account(DOCUMENT_NUMBER);
		account = accountService.saveAccount(account);
		assertNotNull(account);
		assertNotNull(account.getAccountId());
		account = this.accountService.getAccountById(ACCOUNT_ID);
		assertNotNull(account);
		assertEquals(ACCOUNT_ID, account.getAccountId());
		assertEquals(DOCUMENT_NUMBER, account.getDocumentNumber());
	}
	
	@Test
	public void testFailGetById() {
		assertThrows(AccountNotFoundException.class, () -> this.accountService.getAccountById(ACCOUNT_ID_ERROR));
	}
	
	@Test
	public void testFailSave() {
		final Account account = new Account(DOCUMENT_NUMBER_ERROR);
		accountService.saveAccount(account);
		assertThrows(AccountAlreadyExistsException.class, () -> this.accountService.saveAccount(account));
	}
}
