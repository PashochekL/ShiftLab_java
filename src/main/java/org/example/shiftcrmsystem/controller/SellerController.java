package org.example.shiftcrmsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.shiftcrmsystem.DTO.*;
import org.example.shiftcrmsystem.entities.Seller;
import org.example.shiftcrmsystem.services.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Sellers", description = "Operations related to sellers")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sellers")
public class SellerController {
    public final SellerService sellerService;

    @Operation(summary = "Добавить нового продавца")
    @PostMapping("/add")
    public ResponseEntity<Seller> addSeller(@RequestBody SellerDTO sellerDTO) {
        Seller seller = Seller.builder()
                .name(sellerDTO.getName())
                .contactInformation(sellerDTO.getContactInformation())
                .registrationDate(LocalDateTime.now())
                .transactions(new ArrayList<>())
                .build();
        Seller savedSeller = sellerService.createSeller(seller);

        if (savedSeller == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedSeller);
    }

    @Operation(summary = "Получить продавца по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSeller(@PathVariable Long id) {
        return sellerService.getSellerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить всех продавцов")
    @GetMapping("/all")
    public List<Seller> getAllSeller() {
        return sellerService.getAllSellers();
    }

    @Operation(summary = "Получить самого продуктивного продавца")
    @PostMapping("/mostProductive")
    public ResponseEntity<?> getMostProductiveSeller(@RequestBody PeriodFilterDTO filterRequest) {
        return sellerService.getMostProductiveSeller(filterRequest);
    }

    @Operation(summary = "Получить продавцов с минимальной суммой")
    @PostMapping("/minAmount/{amount}")
    public ResponseEntity<?> getSellersWithMinAmount(@PathVariable Long amount, @RequestBody PeriodFilterDTO filterRequest) {
        return sellerService.getSellersWithMinAmount(amount, filterRequest);
    }

    @Operation(summary = "Обновить информацию о продавце")
    @PutMapping("/update/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable Long id, @RequestBody SellerDTO sellerSTO) {
        try {
            Seller updatedSeller = sellerService.updateSeller(id, sellerSTO);
            return ResponseEntity.ok(updatedSeller);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Удалить продавца")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.ok().build();
    }
}
