package br.com.pismo.accounts.resource;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pismo.accounts.model.Account;
import br.com.pismo.accounts.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@RequestMapping(path = "/v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@AllArgsConstructor
public class AccountResource {

	AccountService service;

	@ApiOperation(value = "Operation to get an account by id", nickname = "GetAccountById")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "When the service finds an account with the informed id ", response = Account.class),
            @ApiResponse(code = 404, message = "When the service donesn't find an account with the informed id ", response = String.class)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable("accountId")Long id) {
		return new ResponseEntity<Account>(service.getAccountById(id), HttpStatus.OK);	
	}
	
	@ApiOperation(value = "Operation to save an account", nickname = "SaveAccount")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "When the service can save the informed account ", response = Account.class),
            @ApiResponse(code = 400, message = "When there is an account with the informed document number ", response = String.class)})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<?> saveAccount(@RequestBody(required = true) @Valid Account account) {
		return new ResponseEntity<Account>(service.saveAccount(account), HttpStatus.CREATED);	
	}
	
}
