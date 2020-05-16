package br.com.pismo.transactions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pismo.transactions.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	public List<Transaction> findByAccountId(Long accountId);
	
}
