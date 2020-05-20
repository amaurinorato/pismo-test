package br.com.pismo.transactions.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountTransactionDTO {
	
	private BigDecimal value;
	private Long accountId;

}
