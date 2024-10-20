package org.example.shiftcrmsystem.repositories;

import org.example.shiftcrmsystem.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    @Query("SELECT s, SUM(t.amount) as totalAmount FROM Seller s JOIN s.transactions t " +
            "WHERE t.transactionDate BETWEEN :startDate AND :endDate GROUP BY s ORDER BY totalAmount DESC")
    List<Object[]> findSellersByTotalTransactionAmountForPeriod(@Param("startDate") LocalDateTime startDate,
                                                                @Param("endDate") LocalDateTime endDate);
}
