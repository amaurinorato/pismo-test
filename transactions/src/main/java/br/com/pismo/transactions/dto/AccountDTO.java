package br.com.pismo.transactions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AccountDTO {
	
	@JsonProperty(value = "account_id")
	private Long accountId;
	
	@JsonProperty(value = "document_number")
	private Long documentNumber;

}
