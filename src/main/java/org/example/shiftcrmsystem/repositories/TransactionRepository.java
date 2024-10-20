package org.example.shiftcrmsystem.repositories;

import org.example.shiftcrmsystem.entities.Seller;
import org.example.shiftcrmsystem.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t.seller FROM Transaction t WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
            "GROUP BY t.seller.id HAVING SUM(t.amount) < :amount")
    List<Seller> findSellersWithTotalAmountLessThan(@Param("amount") Long amount,
                                                    @Param("startDate") LocalDateTime startDate,
                                                    @Param("endDate") LocalDateTime endDate);
}

