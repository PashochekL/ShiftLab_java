package org.example.shiftcrmsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.shiftcrmsystem.DTO.TransactionDTO;
import org.example.shiftcrmsystem.entities.Seller;
import org.example.shiftcrmsystem.entities.Transaction;
import org.example.shiftcrmsystem.exception.TransactionNotFoundException;
import org.example.shiftcrmsystem.repositories.SellerRepository;
import org.example.shiftcrmsystem.services.SellerService;
import org.example.shiftcrmsystem.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Transactions", description = "Operations related to transactions")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {
    public final TransactionService transactionService;
    public final SellerService sellerService;

    @Operation(summary = "Add a new transaction")
    @PostMapping("/add/{id}")
    public ResponseEntity<Transaction> addTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        Seller seller = sellerService.getSellerById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));

        Transaction transaction = Transaction.builder()
                .amount(transactionDTO.getAmount())
                .paymentType(transactionDTO.getPaymentType())
                .transactionDate(LocalDateTime.now())
                .seller(seller)
                .build();
        Transaction savedTransaction = transactionService.createTransaction(transaction);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }

    @Operation(summary = "Get transaction by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        return ResponseEntity.ok(transaction);
    }

    @Operation(summary = "Get all transactions")
    @GetMapping("/all")
    public List<Transaction> getAllTransaction() {
        return transactionService.getAllTransactions();
    }

    @Operation(summary = "Get all transactions for a seller by ID")
    @GetMapping("/seller/{id}/transactions")
    public List<Transaction> getAllSellerTransactions(@PathVariable Long id) {
        List<Transaction> transactions = transactionService.getAllTransactions();
        List<Transaction> sellerTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getSeller().getId().equals(id)) {
                sellerTransactions.add(transaction);
            }
        }
        return sellerTransactions;
    }
}
