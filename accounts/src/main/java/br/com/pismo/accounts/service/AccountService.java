package br.com.pismo.accounts.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pismo.accounts.exceptions.AccountAlreadyExistsException;
import br.com.pismo.accounts.exceptions.AccountNotFoundException;
import br.com.pismo.accounts.model.Account;
import br.com.pismo.accounts.repository.AccountRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {

	AccountRepository repository;
	
	public Account getAccountById(Long id) throws AccountNotFoundException {
		return this.repository.findById(id).orElseThrow(() -> {
			throw new AccountNotFoundException("No account found with the informed id: " + id);
		});
	}
	
	public Account saveAccount(Account account) throws AccountNotFoundException, AccountAlreadyExistsException {
		Optional<Account> accountOpt = repository.findByDocumentNumber(account.getDocumentNumber());
		if (accountOpt.isPresent()) {
			throw new AccountAlreadyExistsException("An account already exists with the informed document number: " + account.getDocumentNumber());
		} else {
			return this.repository.save(account);
		}
	}
}
