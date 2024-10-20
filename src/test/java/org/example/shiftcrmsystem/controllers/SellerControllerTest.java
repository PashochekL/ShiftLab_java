package org.example.shiftcrmsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.shiftcrmsystem.DTO.PeriodFilterDTO;
import org.example.shiftcrmsystem.DTO.SellerDTO;
import org.example.shiftcrmsystem.controller.SellerController;
import org.example.shiftcrmsystem.entities.Seller;
import org.example.shiftcrmsystem.services.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SellerController.class)
public class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerService sellerService;

    private Seller seller;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper.registerModule(new JavaTimeModule());
        seller = Seller.builder()
                .id(1L)
                .name("Test Seller")
                .contactInformation("test@example.com")
                .build();
    }

    @Test
    void testAddSeller() throws Exception {
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setName("Test Seller");
        sellerDTO.setContactInformation("test@example.com");

        when(sellerService.createSeller(any(Seller.class))).thenReturn(seller);

        mockMvc.perform(post("/api/sellers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Seller\", \"contactInformation\":\"test@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Seller"))
                .andExpect(jsonPath("$.contactInformation").value("test@example.com"));

        verify(sellerService, times(1)).createSeller(any(Seller.class));
    }

    @Test
    void testGetSellerById() throws Exception {
        when(sellerService.getSellerById(1L)).thenReturn(Optional.of(seller));

        mockMvc.perform(get("/api/sellers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Seller"))
                .andExpect(jsonPath("$.contactInformation").value("test@example.com"));

        verify(sellerService, times(1)).getSellerById(1L);
    }

    @Test
    void testGetSellerNotFound() throws Exception {
        when(sellerService.getSellerById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/sellers/{id}", 999L))
                .andExpect(status().isNotFound());

        verify(sellerService, times(1)).getSellerById(999L);
    }

    @Test
    void testGetAllSellers() throws Exception {
        when(sellerService.getAllSellers()).thenReturn(Collections.singletonList(seller));

        mockMvc.perform(get("/api/sellers/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Seller"))
                .andExpect(jsonPath("$[0].contactInformation").value("test@example.com"));

        verify(sellerService, times(1)).getAllSellers();
    }

    @Test
    void testUpdateSeller() throws Exception {
        SellerDTO updatedSellerDTO = new SellerDTO();
        updatedSellerDTO.setName("Updated Seller");
        updatedSellerDTO.setContactInformation("updated@example.com");

        when(sellerService.updateSeller(eq(1L), any(SellerDTO.class))).thenReturn(seller);

        mockMvc.perform(put("/api/sellers/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Seller\", \"contactInformation\":\"updated@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Seller"))
                .andExpect(jsonPath("$.contactInformation").value("test@example.com"));

        verify(sellerService, times(1)).updateSeller(eq(1L), any(SellerDTO.class));
    }

    @Test
    void testUpdateSellerNotFound() throws Exception {
        SellerDTO updatedSellerDTO = new SellerDTO();
        updatedSellerDTO.setName("Updated Seller");
        updatedSellerDTO.setContactInformation("updated@example.com");

        when(sellerService.updateSeller(eq(999L), any(SellerDTO.class))).thenThrow(new IllegalArgumentException("Seller not found"));

        mockMvc.perform(put("/api/sellers/update/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Seller\", \"contactInformation\":\"updated@example.com\"}"))
                .andExpect(status().isNotFound());

        verify(sellerService, times(1)).updateSeller(eq(999L), any(SellerDTO.class));
    }

    @Test
    void testDeleteSeller() throws Exception {
        doNothing().when(sellerService).deleteSeller(1L);

        mockMvc.perform(delete("/api/sellers/delete/{id}", 1L))
                .andExpect(status().isOk());

        verify(sellerService, times(1)).deleteSeller(1L);
    }

    @Test
    void testGetMostProductiveSeller() throws Exception {
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("DAY");
        filterRequest.setDate(LocalDate.now());

        Seller mostProductiveSeller = Seller.builder()
                .id(1L)
                .name("Best Seller")
                .contactInformation("best_seller@example.com")
                .build();

        when(sellerService.getMostProductiveSeller(any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.ok(mostProductiveSeller));

        mockMvc.perform(post("/api/sellers/mostProductive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filterRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Best Seller"));

        verify(sellerService, times(1)).getMostProductiveSeller(any(PeriodFilterDTO.class));
    }

    @Test
    void testGetMostProductiveSellerNotFound() throws Exception {
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("DAY");
        filterRequest.setDate(LocalDate.now());

        when(sellerService.getMostProductiveSeller(any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(post("/api/sellers/mostProductive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filterRequest)))
                .andExpect(status().isNotFound());

        verify(sellerService, times(1)).getMostProductiveSeller(any(PeriodFilterDTO.class));
    }

    @Test
    void testGetSellersWithMinAmount() throws Exception {
        Long minAmount = 1000L;
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("MONTH");
        filterRequest.setDate(LocalDate.now());

        Seller sellerWithMinAmount = Seller.builder()
                .id(2L)
                .name("Seller with Minimum Amount")
                .contactInformation("seller@example.com")
                .build();

        when(sellerService.getSellersWithMinAmount(eq(minAmount), any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.ok(Collections.singletonList(sellerWithMinAmount)));

        mockMvc.perform(post("/api/sellers/minAmount/{amount}", minAmount)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filterRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Seller with Minimum Amount"));

        verify(sellerService, times(1)).getSellersWithMinAmount(eq(minAmount), any(PeriodFilterDTO.class));
    }
}
