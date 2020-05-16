package br.com.pismo.transactions.service;

import org.springframework.stereotype.Service;

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
		accountClient.getAccountById(transaction.getAccountId());
		return repository.save(transaction);
	}
	
}
