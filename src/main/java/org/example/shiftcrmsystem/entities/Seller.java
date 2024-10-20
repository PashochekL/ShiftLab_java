package org.example.shiftcrmsystem.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.shiftcrmsystem.DTO.SellerDTO;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contactInformation;
    private LocalDateTime registrationDate;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transaction> transactions;

    public Seller() {

    }

    public Seller(SellerDTO sellerDTO) {
        this.name = sellerDTO.getName();
        this.contactInformation = sellerDTO.getContactInformation();
        this.registrationDate = LocalDateTime.now();
    }

    public Seller(long l, String johnDoe, String mail) {
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactInformation='" + contactInformation + '\'' +
                ", transactions=" + (transactions != null ? transactions.size() : 0) +
                '}';
    }
}
