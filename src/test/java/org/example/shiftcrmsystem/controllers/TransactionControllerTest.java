package org.example.shiftcrmsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.shiftcrmsystem.DTO.TransactionDTO;
import org.example.shiftcrmsystem.controller.TransactionController;
import org.example.shiftcrmsystem.entities.PaymentType;
import org.example.shiftcrmsystem.entities.Seller;
import org.example.shiftcrmsystem.entities.Transaction;
import org.example.shiftcrmsystem.services.SellerService;
import org.example.shiftcrmsystem.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.example.shiftcrmsystem.entities.PaymentType.CARD;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private SellerService sellerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Transaction transaction;
    private Seller seller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper.registerModule(new JavaTimeModule());

        seller = Seller.builder()
                .id(1L)
                .name("Test Seller")
                .contactInformation("test@example.com")
                .build();

        transaction = Transaction.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100.00))
                .paymentType(CARD)
                .transactionDate(LocalDateTime.now())
                .seller(seller)
                .build();
    }

    @Test
    void testAddTransaction() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(BigDecimal.valueOf(100.0));
        transactionDTO.setPaymentType(PaymentType.CARD);

        when(sellerService.getSellerById(1L)).thenReturn(Optional.of(seller));
        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions/add/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.paymentType").value("CARD"));

        verify(sellerService, times(1)).getSellerById(1L);
        verify(transactionService, times(1)).createTransaction(any(Transaction.class));
    }

    @Test
    void testAddTransactionSellerNotFound() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(BigDecimal.valueOf(100.0));
        transactionDTO.setPaymentType(CARD);

        when(sellerService.getSellerById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/transactions/add/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isNotFound());

        verify(sellerService, times(1)).getSellerById(999L);
    }

    @Test
    void testGetTransactionById() throws Exception {
        when(transactionService.getTransactionById(1L)).thenReturn(Optional.of(transaction));

        mockMvc.perform(get("/api/transactions/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.paymentType").value("CARD"));

        verify(transactionService, times(1)).getTransactionById(1L);
    }

    @Test
    void testGetTransactionByIdNotFound() throws Exception {
        when(transactionService.getTransactionById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/transactions/{id}", 999L))
                .andExpect(status().isNotFound());

        verify(transactionService, times(1)).getTransactionById(999L);
    }

    @Test
    void testGetAllTransactions() throws Exception {
        when(transactionService.getAllTransactions()).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(get("/api/transactions/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[0].paymentType").value("CARD"));

        verify(transactionService, times(1)).getAllTransactions();
    }

    @Test
    void testGetAllSellerTransactions() throws Exception {
        when(transactionService.getAllTransactions()).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(get("/api/transactions/seller/{id}/transactions", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[0].paymentType").value("CARD"));

        verify(transactionService, times(1)).getAllTransactions();
    }
}
