package br.com.pismo.transactions.model;

import java.math.BigDecimal;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OperationType {

	COMPRA_A_VISTA(1) {
		@Override
		public BigDecimal calcValue(BigDecimal value) {
			return value.negate();
		}
	},
	COMPRA_PARCELADA(2) {
		@Override
		public BigDecimal calcValue(BigDecimal value) {
			return value.negate();
		}
	},
	SAQUE(3) {
		@Override
		public BigDecimal calcValue(BigDecimal value) {
			return value.negate();
		}
	},
	PAGAMENTO(4) {
		@Override
		public BigDecimal calcValue(BigDecimal value) {
			return value;
		}
	};

	@JsonValue
	private int operationTypeId;

	private OperationType(int operationTypeId) {
		this.operationTypeId = operationTypeId;
	}

	public int getOperationTypeId() {
		return operationTypeId;
	}
	
	public abstract BigDecimal calcValue(BigDecimal value);

	public static OperationType of(int operationTypeId) {
		return Stream.of(OperationType.values())
				.filter(p -> p.getOperationTypeId() == operationTypeId)
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

	@JsonCreator
	public static OperationType getNameByValue(final int value) {
		for (final OperationType s: OperationType.values()) {
			if (s.operationTypeId == value) {
				return s;
			}
		}
		return null;
	}
}
