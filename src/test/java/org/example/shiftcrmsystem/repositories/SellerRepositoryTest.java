package org.example.shiftcrmsystem.repositories;

import org.example.shiftcrmsystem.entities.Seller;
import org.example.shiftcrmsystem.entities.Transaction;
import org.example.shiftcrmsystem.entities.PaymentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class SellerRepositoryTest {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Seller seller1;
    private Seller seller2;
    private Seller seller3;

    @BeforeEach
    public void setUp() {
        seller1 = new Seller();
        seller1.setName("Seller 1");
        seller2 = new Seller();
        seller2.setName("Seller 2");
        seller3 = new Seller();
        seller3.setName("Seller 3");

        entityManager.persist(seller1);
        entityManager.persist(seller2);
        entityManager.persist(seller3);

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(new BigDecimal("100.00"));
        transaction1.setPaymentType(PaymentType.CARD);
        transaction1.setTransactionDate(LocalDateTime.now().minusDays(1));
        transaction1.setSeller(seller1);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(new BigDecimal("200.00"));
        transaction2.setPaymentType(PaymentType.CARD);
        transaction2.setTransactionDate(LocalDateTime.now().minusDays(2));
        transaction2.setSeller(seller2);

        Transaction transaction3 = new Transaction();
        transaction3.setAmount(new BigDecimal("300.00"));
        transaction3.setPaymentType(PaymentType.CASH);
        transaction3.setTransactionDate(LocalDateTime.now().minusDays(3));
        transaction3.setSeller(seller1);

        Transaction transaction4 = new Transaction();
        transaction4.setAmount(new BigDecimal("50.00"));
        transaction4.setPaymentType(PaymentType.CARD);
        transaction4.setTransactionDate(LocalDateTime.now().minusDays(1));
        transaction4.setSeller(seller3);

        entityManager.persist(transaction1);
        entityManager.persist(transaction2);
        entityManager.persist(transaction3);
        entityManager.persist(transaction4);
    }

    @Test
    public void testFindSellersByTotalTransactionAmountForPeriod() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(5);
        LocalDateTime endDate = LocalDateTime.now();

        List<Object[]> results = sellerRepository.findSellersByTotalTransactionAmountForPeriod(startDate, endDate);

        assertEquals(3, results.size());

        Object[] result1 = results.get(0);
        Seller topSeller = (Seller) result1[0];
        BigDecimal totalAmount = (BigDecimal) result1[1];
        assertEquals(seller1, topSeller);
        assertEquals(new BigDecimal("400.00"), totalAmount);

        Object[] result2 = results.get(1);
        Seller secondSeller = (Seller) result2[0];
        totalAmount = (BigDecimal) result2[1];
        assertEquals(seller2, secondSeller);
        assertEquals(new BigDecimal("200.00"), totalAmount);

        Object[] result3 = results.get(2);
        Seller thirdSeller = (Seller) result3[0];
        totalAmount = (BigDecimal) result3[1];
        assertEquals(seller3, thirdSeller);
        assertEquals(new BigDecimal("50.00"), totalAmount);
    }
}
