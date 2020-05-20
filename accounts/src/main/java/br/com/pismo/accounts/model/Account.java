package br.com.pismo.accounts.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@ApiModel(value = "Account", description = "Class to hold account data")
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(value = "account_id")
	private Long accountId;
	
	@ApiModelProperty(value = "Account document number", required = true)
    @NotNull(message = "Document_number must not be null")
    @NonNull
    @Column(unique = true, name = "document_number")
	@JsonProperty(value = "document_number")
	@NumberFormat(style = Style.NUMBER)
	private Long documentNumber;
	
	@ApiModelProperty(value = "Available Credit Limit", required = true)
	@NotNull(message = "Available credit limit must be informed")
	@NonNull
	@Column(name="available_credit_limit")
	@JsonProperty(value = "available_credit_limit")
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal availableCreditLimit;
	
	
}
