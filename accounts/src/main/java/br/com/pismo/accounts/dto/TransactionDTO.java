package br.com.pismo.accounts.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
	
	@NotNull
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal value;
	
	@NotNull
	private Long accountId;

}
