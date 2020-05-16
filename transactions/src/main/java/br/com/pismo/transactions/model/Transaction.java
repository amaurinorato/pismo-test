package br.com.pismo.transactions.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

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
@ApiModel(value = "Transaction", description = "Class to hold transaction data")
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(value = "transaction_id")
	private Long transactionId;
	
	@NotNull
    @NonNull
	@JsonProperty(value = "account_id")
	@Column(name="account_id")
	@ApiModelProperty(value = "Account document number", required = true, example = "1")
	private Long accountId;
	
    @NotNull
    @NonNull
    @Column(name = "operation_type_id")
	@JsonProperty(value = "operation_type_id")
	@Enumerated(EnumType.ORDINAL)
    @ApiModelProperty(value = "Operation type id", required = true, example = "1", allowableValues ="range[1,4]" )
	private OperationType operationTypeId;
	
    @NotNull
    @NonNull
    @ApiModelProperty(value = "Amount", required = true, example = "125.45")
	private BigDecimal amount;
	
    @NonNull
    @ApiModelProperty(value = "Event Date", required = true)
    @Column(name="event_date")
	private LocalDateTime eventDate;
    
    @PrePersist
    public void prePersist() {
        this.eventDate = LocalDateTime.now();
        this.amount = operationTypeId.calcValue(this.amount);
    }
    
}
