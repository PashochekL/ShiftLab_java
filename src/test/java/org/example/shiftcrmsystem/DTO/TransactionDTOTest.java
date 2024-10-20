package org.example.shiftcrmsystem.DTO;

import org.example.shiftcrmsystem.entities.PaymentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDTOTest {

    @Test
    void testConstructorWithParameters() {
        BigDecimal amount = new BigDecimal("100.00");
        PaymentType paymentType = PaymentType.CARD;

        TransactionDTO transactionDTO = new TransactionDTO(amount, paymentType);

        assertEquals(amount, transactionDTO.getAmount());
        assertEquals(paymentType, transactionDTO.getPaymentType());
    }

    @Test
    void testDefaultConstructor() {
        TransactionDTO transactionDTO = new TransactionDTO();

        assertNull(transactionDTO.getAmount());
        assertNull(transactionDTO.getPaymentType());
    }

    @Test
    void testSettersAndGetters() {
        TransactionDTO transactionDTO = new TransactionDTO();

        BigDecimal amount = new BigDecimal("200.00");
        PaymentType paymentType = PaymentType.CASH;

        transactionDTO.setAmount(amount);
        transactionDTO.setPaymentType(paymentType);

        assertEquals(amount, transactionDTO.getAmount());
        assertEquals(paymentType, transactionDTO.getPaymentType());
    }

    @Test
    void testNullFields() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(null);
        transactionDTO.setPaymentType(null);

        assertNull(transactionDTO.getAmount());
        assertNull(transactionDTO.getPaymentType());
    }

    @Test
    void testZeroAmount() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(BigDecimal.ZERO);

        assertEquals(BigDecimal.ZERO, transactionDTO.getAmount());
    }

    @Test
    void testNegativeAmount() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(new BigDecimal("-50.00"));

        assertEquals(new BigDecimal("-50.00"), transactionDTO.getAmount());
    }

    @Test
    void testEqualsAndHashCode() {
        TransactionDTO transactionDTO1 = new TransactionDTO(new BigDecimal("100.00"), PaymentType.CARD);
        TransactionDTO transactionDTO2 = new TransactionDTO(new BigDecimal("100.00"), PaymentType.CARD);
        TransactionDTO transactionDTO3 = new TransactionDTO(new BigDecimal("200.00"), PaymentType.CASH);

        assertEquals(transactionDTO1, transactionDTO2);
        assertNotEquals(transactionDTO1, transactionDTO3);
        assertEquals(transactionDTO1.hashCode(), transactionDTO2.hashCode());
    }

    @Test
    void testToString() {
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal("100.00"), PaymentType.CARD);
        String expectedString = "TransactionDTO(amount=100.00, paymentType=CARD)";
        assertEquals(expectedString, transactionDTO.toString());
    }
}
