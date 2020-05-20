package br.com.pismo.transactions.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.pismo.transactions.dto.AccountTransactionDTO;
import br.com.pismo.transactions.dto.AccountDTO;

@FeignClient(name="accounts")
public interface AccountRestClient {
	
	@GetMapping("/v1/accounts/{id}")
	public ResponseEntity<AccountDTO> getAccountById(@PathVariable("id") Long accountId);
	
	@PostMapping("/v1/accounts/transaction")
	public ResponseEntity<AccountDTO> postTransaction(@RequestBody AccountTransactionDTO account);

}
