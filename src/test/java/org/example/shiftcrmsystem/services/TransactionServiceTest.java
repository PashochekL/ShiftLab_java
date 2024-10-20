package org.example.shiftcrmsystem.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.shiftcrmsystem.DTO.TransactionDTO;
import org.example.shiftcrmsystem.controller.TransactionController;
import org.example.shiftcrmsystem.entities.PaymentType;
import org.example.shiftcrmsystem.entities.Seller;
import org.example.shiftcrmsystem.entities.Transaction;
import org.example.shiftcrmsystem.exception.GlobalExceptionHandler;
import org.example.shiftcrmsystem.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@ComponentScan(basePackages = "org.example.shiftcrmsystem")
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private SellerService sellerService;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testAddTransaction() throws Exception {
        Long sellerId = 1L;
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal("100.00"), PaymentType.CASH);

        Seller seller = new Seller();
        seller.setId(sellerId);
        seller.setName("Test Seller");

        Transaction savedTransaction = Transaction.builder()
                .id(1L)
                .amount(transactionDTO.getAmount())
                .paymentType(transactionDTO.getPaymentType())
                .transactionDate(LocalDateTime.now())
                .seller(seller)
                .build();

        when(sellerService.getSellerById(sellerId)).thenReturn(Optional.of(seller));
        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(savedTransaction);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(transactionDTO);

        mockMvc.perform(post("/api/transactions/add/{id}", sellerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(100.00))
                .andExpect(jsonPath("$.paymentType").value("CASH"));

        verify(sellerService, times(1)).getSellerById(sellerId);
        verify(transactionService, times(1)).createTransaction(any(Transaction.class));
    }

    @Test
    void testAddTransactionSellerNotFound() throws Exception {
        Long sellerId = 999L;
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal("100.00"), PaymentType.CASH);

        when(sellerService.getSellerById(sellerId)).thenReturn(Optional.empty());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(transactionDTO);

        mockMvc.perform(post("/api/transactions/add/{id}", sellerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Seller not found"));

        verify(sellerService, times(1)).getSellerById(sellerId);
        verify(transactionService, never()).createTransaction(any(Transaction.class));
    }

    @Test
    void testGetAllTransactions() throws Exception {
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setAmount(new BigDecimal("100.00"));
        transaction1.setPaymentType(PaymentType.CASH);

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAmount(new BigDecimal("200.00"));
        transaction2.setPaymentType(PaymentType.CARD);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        when(transactionService.getAllTransactions()).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].amount").value("100.0"))
                .andExpect(jsonPath("$[1].amount").value("200.0"));

        verify(transactionService, times(1)).getAllTransactions();
    }

    @Test
    void testGetTransactionById() throws Exception {
        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setPaymentType(PaymentType.CASH);

        when(transactionService.getTransactionById(transactionId)).thenReturn(Optional.of(transaction));

        mockMvc.perform(get("/api/transactions/{id}", transactionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value("100.0"))
                .andExpect(jsonPath("$.paymentType").value("CASH"));

        verify(transactionService, times(1)).getTransactionById(transactionId);
    }

    @Test
    void testGetTransactionNotFound() throws Exception {
        Long transactionId = 999L;

        when(transactionService.getTransactionById(transactionId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/transactions/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Transaction not found"));

        verify(transactionService, times(1)).getTransactionById(transactionId);
    }

    @Test
    void testGetTransactionById_Success() {
        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);

        when(transactionService.getTransactionById(transactionId)).thenReturn(Optional.of(transaction));

        Optional<Transaction> result = transactionService.getTransactionById(transactionId);

        assertTrue(result.isPresent());
        assertEquals(transactionId, result.get().getId());
        verify(transactionService, times(1)).getTransactionById(transactionId);
    }
}
