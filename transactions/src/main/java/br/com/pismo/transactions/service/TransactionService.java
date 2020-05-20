package br.com.pismo.transactions.service;

import org.springframework.stereotype.Service;

import br.com.pismo.transactions.dto.AccountTransactionDTO;
import br.com.pismo.transactions.model.OperationType;
import br.com.pismo.transactions.model.Transaction;
import br.com.pismo.transactions.repository.TransactionRepository;
import br.com.pismo.transactions.restclient.AccountRestClient;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {

	AccountRestClient accountClient;
	TransactionRepository repository;
	
	public Transaction saveTransaction(Transaction transaction) {
		Long accountId = transaction.getAccountId();
		accountClient.getAccountById(accountId).getBody();
		accountClient.postTransaction(new AccountTransactionDTO(transaction.getOperationTypeId().calcValue(transaction.getAmount()), accountId)).getBody();
		return repository.save(transaction);
	}
}
