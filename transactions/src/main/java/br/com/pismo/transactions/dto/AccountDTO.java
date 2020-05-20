package br.com.pismo.transactions.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
	
	@JsonProperty(value = "account_id")
	private Long accountId;
	
	@JsonProperty(value = "document_number")
	private Long documentNumber;
	
	@JsonProperty(value = "available_credit_limit")
	private BigDecimal availableCreditLimit;

}
