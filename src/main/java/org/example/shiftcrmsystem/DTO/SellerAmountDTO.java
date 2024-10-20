package org.example.shiftcrmsystem.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class SellerAmountDTO {
    private Long sellerId;
    private String sellerName;
    private BigDecimal totalAmount;
    private List<TransactionDTO> transactions;

    public SellerAmountDTO(Long sellerId, String sellerName, BigDecimal totalAmount, List<TransactionDTO> transactions) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.totalAmount = totalAmount;
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Seller: " + sellerName + " (ID: " + sellerId + "), Total Amount: " + totalAmount;
    }
}
