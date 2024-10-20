package org.example.shiftcrmsystem.services;

import org.example.shiftcrmsystem.DTO.PeriodFilterDTO;
import org.example.shiftcrmsystem.DTO.SellerAmountDTO;
import org.example.shiftcrmsystem.DTO.SellerDTO;
import org.example.shiftcrmsystem.DTO.TransactionDTO;
import org.example.shiftcrmsystem.entities.Seller;
import org.example.shiftcrmsystem.entities.Transaction;
import org.example.shiftcrmsystem.repositories.SellerRepository;
import org.example.shiftcrmsystem.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SellerService {
    public final SellerRepository sellerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public Optional<Seller> getSellerById(Long id) {
        return sellerRepository.findById(id);
    }

    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public Seller updateSeller(Long id, SellerDTO sellerSTO) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));
        seller.setName(sellerSTO.getName());
        seller.setContactInformation(sellerSTO.getContactInformation());
        return sellerRepository.save(seller);
    }

    public void deleteSeller(Long id) {
        sellerRepository.deleteById(id);
    }

    public ResponseEntity<Seller> getMostProductiveSeller(PeriodFilterDTO filterDTO) {
        LocalDate date = filterDTO.getDate();
        String periodType = filterDTO.getPeriodType();

        switch (periodType) {
            case "DAY":
                return handleDay(date);
            case "MONTH":
                return handleMonth(date);
            case "QUARTER":
                return handleQuarter(date);
            case "YEAR":
                return handleYear(date);
            default:
                return ResponseEntity.badRequest().body(null);
        }
    }

    private ResponseEntity<Seller> handleDay(LocalDate date) {
        List<Seller> mostProductiveSellers = findMostProductiveSellerByDate(date);
        if (mostProductiveSellers.isEmpty()) {
            return ResponseEntity.ok(null);
        }
        Seller mostProductiveSeller = mostProductiveSellers.get(0);
        return ResponseEntity.ok(mostProductiveSeller);
    }

    private ResponseEntity<Seller> handleMonth(LocalDate date) {
        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());
        return handlePeriod(startDate, endDate, "месяц");
    }

    public ResponseEntity<Seller> handleQuarter(LocalDate date) {
        int month = date.getMonthValue();
        LocalDate startDate;
        LocalDate endDate;

        if (month <= 3) {
            startDate = date.withMonth(1).withDayOfMonth(1);
            endDate = date.withMonth(3).withDayOfMonth(31);
        } else if (month <= 6) {
            startDate = date.withMonth(4).withDayOfMonth(1);
            endDate = date.withMonth(6).withDayOfMonth(30);
        } else if (month <= 9) {
            startDate = date.withMonth(7).withDayOfMonth(1);
            endDate = date.withMonth(9).withDayOfMonth(30);
        } else {
            startDate = date.withMonth(10).withDayOfMonth(1);
            endDate = date.withMonth(12).withDayOfMonth(31);
        }
        return handlePeriod(startDate, endDate, "квартал");
    }

    private ResponseEntity<Seller> handleYear(LocalDate date) {
        LocalDate startDate = date.withDayOfYear(1);
        LocalDate endDate = date.withDayOfYear(date.lengthOfYear());
        return handlePeriod(startDate, endDate, "год");
    }

    ResponseEntity<Seller> handlePeriod(LocalDate startDate, LocalDate endDate, String periodName) {
        List<Seller> mostProductiveSellers = findMostProductiveSellerByPeriod(startDate, endDate);
        if (mostProductiveSellers.isEmpty()) {
            return ResponseEntity.ok(null);
        }
        Seller mostProductiveSeller = mostProductiveSellers.get(0);
        return ResponseEntity.ok(mostProductiveSeller);
    }

    public List<Seller> findMostProductiveSellerByDate(LocalDate date) {
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.plusDays(1).atStartOfDay();

        List<Object[]> results = sellerRepository.findSellersByTotalTransactionAmountForPeriod(startDate, endDate);
        List<Seller> sellers = new ArrayList<>();

        for (Object[] result : results) {
            Seller seller = (Seller) result[0];
            sellers.add(seller);
        }
        return sellers;
    }

    public List<Seller> findMostProductiveSellerByPeriod(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = sellerRepository.findSellersByTotalTransactionAmountForPeriod(startDate.atStartOfDay(), endDate.atStartOfDay());
        List<Seller> sellers = new ArrayList<>();

        for (Object[] result : results) {
            Seller seller = (Seller) result[0];
            sellers.add(seller);
        }
        return sellers;
    }

    public ResponseEntity<List<Seller>> getSellersWithMinAmount(Long amount, PeriodFilterDTO filterDTO) {
        LocalDate startDate = filterDTO.getDate();
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIDNIGHT);
        LocalDateTime endDateTime;

        switch (filterDTO.getPeriodType()) {
            case "DAY":
                endDateTime = startDateTime.plus(1, ChronoUnit.DAYS);
                break;
            case "MONTH":
                endDateTime = startDateTime.plus(1, ChronoUnit.MONTHS);
                break;
            case "QUARTER":
                endDateTime = startDateTime.plus(3, ChronoUnit.MONTHS);
                break;
            case "YEAR":
                endDateTime = startDateTime.plus(1, ChronoUnit.YEARS);
                break;
            default:
                return ResponseEntity.badRequest().body(null);
        }

        List<Seller> sellersWithMinAmount = transactionRepository
                .findSellersWithTotalAmountLessThan(amount, startDateTime, endDateTime);

        return ResponseEntity.ok(sellersWithMinAmount);
    }
}
