package org.example.shiftcrmsystem.DTO;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.shiftcrmsystem.entities.PaymentType;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDTO {
    BigDecimal amount;
    PaymentType paymentType;

    public TransactionDTO(BigDecimal amount, PaymentType paymentType) {
        this.amount = amount;
        this.paymentType = paymentType;
    }

    public TransactionDTO() {

    }
}
