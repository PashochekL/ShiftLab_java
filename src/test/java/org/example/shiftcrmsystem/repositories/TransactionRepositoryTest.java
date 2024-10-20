package org.example.shiftcrmsystem.repositories;

import jakarta.transaction.Transactional;
import org.example.shiftcrmsystem.entities.PaymentType;
import org.example.shiftcrmsystem.entities.Seller;
import org.example.shiftcrmsystem.entities.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SellerRepository sellerRepository;

    private Seller seller1;
    private Seller seller2;
    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    public void setUp() {
        seller1 = Seller.builder()
                .name("Seller One")
                .contactInformation("seller1@example.com")
                .build();

        seller2 = Seller.builder()
                .name("Seller Two")
                .contactInformation("seller2@example.com")
                .build();

        seller1 = sellerRepository.save(seller1);
        seller2 = sellerRepository.save(seller2);

        transaction1 = Transaction.builder()
                .amount(BigDecimal.valueOf(150.00))
                .paymentType(PaymentType.CARD)
                .transactionDate(LocalDateTime.now().minusDays(1))
                .seller(seller1)
                .build();

        transaction2 = Transaction.builder()
                .amount(BigDecimal.valueOf(200.00))
                .paymentType(PaymentType.CASH)
                .transactionDate(LocalDateTime.now().minusDays(2))
                .seller(seller2)
                .build();

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
    }

    @Test
    void testFindSellersWithTotalAmountLessThan() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(3);
        LocalDateTime endDate = LocalDateTime.now();

        List<Seller> sellers = transactionRepository.findSellersWithTotalAmountLessThan(300L, startDate, endDate);

        assertThat(sellers).hasSize(2);
        assertThat(sellers).containsExactly(seller1, seller2);
    }

    @Test
    void testFindSellersWithTotalAmountLessThan_noSellers() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(3);
        LocalDateTime endDate = LocalDateTime.now();

        List<Seller> sellers = transactionRepository.findSellersWithTotalAmountLessThan(150L, startDate, endDate);

        assertThat(sellers).isEmpty();
    }
}

