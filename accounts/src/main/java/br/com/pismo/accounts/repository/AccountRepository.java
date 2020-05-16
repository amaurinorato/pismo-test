package br.com.pismo.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pismo.accounts.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	public Optional<Account> findByDocumentNumber(Long documentNumber);

}
