package org.example.shiftcrmsystem.DTO;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SellerDTO {
    private String name;
    private String contactInformation;

    public SellerDTO(String name, String contactInformation) {
        this.name = name;
        this.contactInformation = contactInformation;
    }

    public SellerDTO() {

    }
}
