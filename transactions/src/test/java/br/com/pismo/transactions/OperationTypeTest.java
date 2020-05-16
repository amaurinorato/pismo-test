package br.com.pismo.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.com.pismo.transactions.model.OperationType;

public class OperationTypeTest {

	BigDecimal amount = BigDecimal.valueOf(10.55);

	@Test
	public void testCalculoCompraAVista() {
		assertEquals(amount.negate(), OperationType.COMPRA_A_VISTA.calcValue(amount));
	}
	
	@Test
	public void testCalculoCompraParcelada() {
		assertEquals(amount.negate(), OperationType.COMPRA_PARCELADA.calcValue(amount));
	}
	
	@Test
	public void testCalculoSaque() {
		assertEquals(amount.negate(), OperationType.SAQUE.calcValue(amount));
	}
	
	@Test
	public void testCalculoPagamento() {
		assertEquals(amount, OperationType.PAGAMENTO.calcValue(amount));
	}
	
}
