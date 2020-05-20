package br.com.pismo.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.com.pismo.transactions.dto.AccountDTO;
import br.com.pismo.transactions.dto.AccountTransactionDTO;
import br.com.pismo.transactions.model.OperationType;
import br.com.pismo.transactions.model.Transaction;
import br.com.pismo.transactions.repository.TransactionRepository;
import br.com.pismo.transactions.restclient.AccountRestClient;
import br.com.pismo.transactions.service.TransactionService;
import feign.FeignException;


@SpringBootTest
@ActiveProfiles("test")
class TransactionsServiceTests {
	
	
	private static final BigDecimal INITIAL_LIMIT = BigDecimal.valueOf(10.5);

	@Autowired
	private TransactionRepository repository;

    private TransactionService transactionService;
    
    @MockBean
    private AccountRestClient accountClient;
    
    private Transaction transactionSuccess;
    
    private AccountTransactionDTO accountTransactionDTOCreditSuccess;
    private AccountTransactionDTO accountTransactionDTODebitSuccess;
    private AccountTransactionDTO accountTransactionDTOError;
    
    private final BigDecimal AMOUNT = BigDecimal.valueOf(1);
    private final Long ACCOUNT_ID = 1L;
    private final Long DOCUMENT_NUMBER = 123456789L;
    private final BigDecimal CREDIT_VALUE_SUCESS = BigDecimal.valueOf(1);
    private final BigDecimal DEBIT_VALUE_SUCESS = BigDecimal.valueOf(-1);
    private final BigDecimal DEBIT_VALUE_ERROR = BigDecimal.valueOf(-11);
    
    
    @BeforeEach
    void init() {
    	AccountDTO accountFindById = createAccount();
    	
    	accountTransactionDTOCreditSuccess = new AccountTransactionDTO(CREDIT_VALUE_SUCESS, ACCOUNT_ID);
    	accountTransactionDTOError = new AccountTransactionDTO(DEBIT_VALUE_ERROR, ACCOUNT_ID);
    	accountTransactionDTODebitSuccess = new AccountTransactionDTO(DEBIT_VALUE_SUCESS, ACCOUNT_ID);
    	
		createTransactionSuccess();
		
		when(accountClient.getAccountById(1L)).thenReturn(new ResponseEntity<AccountDTO>(accountFindById, HttpStatus.OK));
		when(accountClient.getAccountById(2L)).thenThrow(new FeignException(404, "Account not found") {
			private static final long serialVersionUID = 1L;
		});
		when(accountClient.postTransaction(accountTransactionDTOCreditSuccess)).thenReturn(new ResponseEntity<AccountDTO>(new AccountDTO(ACCOUNT_ID, DOCUMENT_NUMBER, INITIAL_LIMIT.add(CREDIT_VALUE_SUCESS)), HttpStatus.ACCEPTED));
		when(accountClient.postTransaction(accountTransactionDTODebitSuccess)).thenReturn(new ResponseEntity<AccountDTO>(new AccountDTO(ACCOUNT_ID, DOCUMENT_NUMBER, INITIAL_LIMIT.subtract(DEBIT_VALUE_SUCESS)), HttpStatus.ACCEPTED));
		when(accountClient.postTransaction(accountTransactionDTOError)).thenThrow(new FeignException(400, "The informed account doesn't have enough balance to make the transaction") {
			private static final long serialVersionUID = 1L;
		});
		
		transactionService = new TransactionService(accountClient, repository);
    }

	@Test
	void testSaveSuccessCompraAVista() {
		Transaction c = this.transactionService.saveTransaction(transactionSuccess);
		assertNotNull(c);
		assertNotNull(c.getEventDate());
		assertNotNull(c.getAccountId());
		assertEquals(AMOUNT.negate(), c.getAmount());
		assertEquals(OperationType.COMPRA_A_VISTA, c.getOperationTypeId());
	}
	
	@Test
	void testSaveSuccessCompraParcelada() {
		transactionSuccess.setOperationTypeId(OperationType.COMPRA_PARCELADA);
		Transaction c = this.transactionService.saveTransaction(transactionSuccess);
		assertNotNull(c);
		assertNotNull(c.getAccountId());
		assertEquals(AMOUNT.negate(), c.getAmount());
		assertNotNull(c.getEventDate());
		assertEquals(OperationType.COMPRA_PARCELADA, c.getOperationTypeId());
	}
	
	@Test
	void testSaveSuccessSaque() {
		transactionSuccess.setOperationTypeId(OperationType.SAQUE);
		Transaction c = this.transactionService.saveTransaction(transactionSuccess);
		assertNotNull(c);
		assertNotNull(c.getAccountId());
		assertEquals(AMOUNT.negate(), c.getAmount());
		assertNotNull(c.getEventDate());
		assertEquals(OperationType.SAQUE, c.getOperationTypeId());
	}
	
	@Test
	void testSaveTransactionAccountNotFound() {
		transactionSuccess.setAccountId(2L);
		assertThrows(FeignException.class, () -> this.transactionService.saveTransaction(transactionSuccess));
	}
	
	@Test
	void testSaveSuccessPagamento() {
		transactionSuccess.setOperationTypeId(OperationType.PAGAMENTO);
		Transaction c = this.transactionService.saveTransaction(transactionSuccess);
		assertNotNull(c);
		assertNotNull(c.getAccountId());
		assertEquals(AMOUNT, c.getAmount());
		assertNotNull(c.getEventDate());
		assertEquals(OperationType.PAGAMENTO, c.getOperationTypeId());
	}

	private AccountDTO createAccount() {
		AccountDTO accountFindById = new AccountDTO();
		accountFindById.setAccountId(ACCOUNT_ID);
		accountFindById.setDocumentNumber(DOCUMENT_NUMBER);
		accountFindById.setAvailableCreditLimit(INITIAL_LIMIT);
		return accountFindById;
	}
	
	private void createTransactionSuccess() {
		transactionSuccess = new Transaction();
		transactionSuccess.setAccountId(ACCOUNT_ID);
		transactionSuccess.setAmount(AMOUNT);
		transactionSuccess.setOperationTypeId(OperationType.COMPRA_A_VISTA);
	}
}
