package org.example.shiftcrmsystem.DTO;

import org.example.shiftcrmsystem.entities.PaymentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SellerAmountDTOTest {

    @Test
    void testConstructorWithParameters() {
        Long sellerId = 1L;
        String sellerName = "Test Seller";
        BigDecimal totalAmount = new BigDecimal("500.00");
        TransactionDTO transaction1 = new TransactionDTO(new BigDecimal("200.00"), PaymentType.CARD);
        TransactionDTO transaction2 = new TransactionDTO(new BigDecimal("300.00"), PaymentType.CASH);
        List<TransactionDTO> transactions = Arrays.asList(transaction1, transaction2);

        SellerAmountDTO sellerAmountDTO = new SellerAmountDTO(sellerId, sellerName, totalAmount, transactions);

        assertEquals(sellerId, sellerAmountDTO.getSellerId());
        assertEquals(sellerName, sellerAmountDTO.getSellerName());
        assertEquals(totalAmount, sellerAmountDTO.getTotalAmount());
        assertEquals(transactions, sellerAmountDTO.getTransactions());
    }

    @Test
    void testDefaultConstructor() {
        SellerAmountDTO sellerAmountDTO = new SellerAmountDTO(null, null, null, null);

        assertNull(sellerAmountDTO.getSellerId());
        assertNull(sellerAmountDTO.getSellerName());
        assertNull(sellerAmountDTO.getTotalAmount());
        assertNull(sellerAmountDTO.getTransactions());
    }

    @Test
    void testSettersAndGetters() {
        SellerAmountDTO sellerAmountDTO = new SellerAmountDTO(null, null, null, null);

        Long sellerId = 1L;
        String sellerName = "Updated Seller";
        BigDecimal totalAmount = new BigDecimal("600.00");
        List<TransactionDTO> transactions = Collections.emptyList();

        sellerAmountDTO.setSellerId(sellerId);
        sellerAmountDTO.setSellerName(sellerName);
        sellerAmountDTO.setTotalAmount(totalAmount);
        sellerAmountDTO.setTransactions(transactions);

        assertEquals(sellerId, sellerAmountDTO.getSellerId());
        assertEquals(sellerName, sellerAmountDTO.getSellerName());
        assertEquals(totalAmount, sellerAmountDTO.getTotalAmount());
        assertEquals(transactions, sellerAmountDTO.getTransactions());
    }

    @Test
    void testToString() {
        Long sellerId = 1L;
        String sellerName = "Test Seller";
        BigDecimal totalAmount = new BigDecimal("500.00");

        SellerAmountDTO sellerAmountDTO = new SellerAmountDTO(sellerId, sellerName, totalAmount, null);
        String expectedString = "Seller: Test Seller (ID: 1), Total Amount: 500.00";

        assertEquals(expectedString, sellerAmountDTO.toString());
    }
}
