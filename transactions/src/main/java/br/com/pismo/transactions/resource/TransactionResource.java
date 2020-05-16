package br.com.pismo.transactions.resource;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pismo.transactions.model.Transaction;
import br.com.pismo.transactions.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@RequestMapping(path = "/v1/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@AllArgsConstructor
public class TransactionResource {
	
	TransactionService service;
	
	@ApiOperation(value = "Operation to save a transaction", nickname = "SaveTransaction")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "When the service can save the informed transaction ", response = Transaction.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "When there is some invalid data in the request ", response = String.class)})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<?> saveTransaction(@RequestBody(required = true) @Valid Transaction transaction) {
		return new ResponseEntity<Transaction>(service.saveTransaction(transaction), HttpStatus.CREATED);	
	}

}
