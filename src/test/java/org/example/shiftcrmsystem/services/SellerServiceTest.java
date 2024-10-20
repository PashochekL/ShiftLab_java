package org.example.shiftcrmsystem.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.shiftcrmsystem.DTO.PeriodFilterDTO;
import org.example.shiftcrmsystem.DTO.SellerDTO;
import org.example.shiftcrmsystem.controller.SellerController;
import org.example.shiftcrmsystem.entities.Seller;
import org.example.shiftcrmsystem.repositories.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@ActiveProfiles("test")
public class SellerServiceTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private SellerService sellerService;

    @InjectMocks
    private SellerController sellerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sellerController).build();
    }

    @Test
    void testAddSeller() throws Exception {
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setName("New Seller");
        sellerDTO.setContactInformation("Contact Info");

        Seller savedSeller = Seller.builder()
                .id(1L)
                .name("New Seller")
                .contactInformation("Contact Info")
                .registrationDate(LocalDateTime.now())
                .build();

        when(sellerService.createSeller(any(Seller.class))).thenReturn(savedSeller);

        mockMvc.perform(post("/api/sellers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sellerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Seller"))
                .andExpect(jsonPath("$.contactInformation").value("Contact Info"));

        verify(sellerService, times(1)).createSeller(any(Seller.class));
    }

    @Test
    void testAddSeller_Success() throws Exception {
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setName("New Seller");
        sellerDTO.setContactInformation("Contact Info");

        Seller savedSeller = Seller.builder()
                .id(1L)
                .name("New Seller")
                .contactInformation("Contact Info")
                .registrationDate(LocalDateTime.now())
                .build();

        when(sellerService.createSeller(any(Seller.class))).thenReturn(savedSeller);

        mockMvc.perform(post("/api/sellers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sellerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Seller"))
                .andExpect(jsonPath("$.contactInformation").value("Contact Info"));

        verify(sellerService, times(1)).createSeller(any(Seller.class));
    }

    @Test
    void testGetSeller() throws Exception {
        Long sellerId = 1L;
        Seller seller = new Seller();
        seller.setId(sellerId);
        seller.setName("Test Seller");
        seller.setContactInformation("Test info");
        seller.setRegistrationDate(LocalDateTime.now());

        when(sellerService.getSellerById(sellerId)).thenReturn(Optional.of(seller));

        mockMvc.perform(get("/api/sellers/{id}", sellerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Seller"))
                .andExpect(jsonPath("$.contactInformation").value("Test info"));

        verify(sellerService, times(1)).getSellerById(sellerId);
    }

    @Test
    void testGetSellerNotFound() throws Exception {
        Long sellerId = 1L;

        when(sellerService.getSellerById(sellerId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/sellers/{id}", sellerId))
                .andExpect(status().isNotFound());

        verify(sellerService, times(1)).getSellerById(sellerId);
    }

    @Test
    void testGetSellerById_Success() throws Exception {
        Long sellerId = 1L;
        Seller seller = new Seller();
        seller.setId(sellerId);
        seller.setName("Тестовый Продавец");
        seller.setContactInformation("Тестовая информация");
        seller.setRegistrationDate(LocalDateTime.now());

        when(sellerService.getSellerById(sellerId)).thenReturn(Optional.of(seller));

        mockMvc.perform(get("/api/sellers/{id}", sellerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sellerId))
                .andExpect(jsonPath("$.name").value("Тестовый Продавец"))
                .andExpect(jsonPath("$.contactInformation").value("Тестовая информация"));

        verify(sellerService, times(1)).getSellerById(sellerId);
    }

    @Test
    void testGetSellerById_NotFound() throws Exception {
        Long sellerId = 1L;

        when(sellerService.getSellerById(sellerId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/sellers/{id}", sellerId))
                .andExpect(status().isNotFound());

        verify(sellerService, times(1)).getSellerById(sellerId);
    }

    @Test
    public void testGetAllSellers() {
        Seller seller1 = new Seller();
        seller1.setId(1L);
        seller1.setName("Seller 1");

        Seller seller2 = new Seller();
        seller2.setId(2L);
        seller2.setName("Seller 2");

        List<Seller> sellers = Arrays.asList(seller1, seller2);

        when(sellerService.getAllSellers()).thenReturn(sellers);

        List<Seller> result = sellerController.getAllSeller();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Seller 1", result.get(0).getName());
        assertEquals("Seller 2", result.get(1).getName());

        verify(sellerService, times(1)).getAllSellers();
    }

    @Test
    void testGetSellerById() throws Exception {
        Long sellerId = 1L;
        Seller seller = new Seller();
        seller.setId(sellerId);
        seller.setName("Test Seller");

        when(sellerService.getSellerById(sellerId)).thenReturn(Optional.of(seller));

        mockMvc.perform(get("/api/sellers/{id}", sellerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Seller"));

        verify(sellerService, times(1)).getSellerById(sellerId);
    }

    @Test
    public void testUpdateSeller() {
        Long sellerId = 1L;
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setName("Updated Seller");
        sellerDTO.setContactInformation("Updated Contact");

        Seller updatedSeller = new Seller();
        updatedSeller.setId(sellerId);
        updatedSeller.setName("Updated Seller");
        updatedSeller.setContactInformation("Updated Contact");

        when(sellerService.updateSeller(eq(sellerId), any(SellerDTO.class))).thenReturn(updatedSeller);

        ResponseEntity<Seller> responseEntity = sellerController.updateSeller(sellerId, sellerDTO);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("Updated Seller", responseEntity.getBody().getName());

        verify(sellerService, times(1)).updateSeller(eq(sellerId), any(SellerDTO.class));
    }

    @Test
    public void testUpdateSellerNotFound() {
        Long sellerId = 999L;
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setName("Non-existent Seller");

        when(sellerService.updateSeller(eq(sellerId), any(SellerDTO.class))).thenThrow(new IllegalArgumentException("Seller not found"));

        ResponseEntity<Seller> responseEntity = sellerController.updateSeller(sellerId, sellerDTO);

        assertEquals(404, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());

        verify(sellerService, times(1)).updateSeller(eq(sellerId), any(SellerDTO.class));
    }

    @Test
    void testDeleteSeller() throws Exception {
        Long sellerId = 1L;

        mockMvc.perform(delete("/api/sellers/delete/{id}", sellerId))
                .andExpect(status().isOk());

        verify(sellerService, times(1)).deleteSeller(sellerId);
    }

    @Test
    void testGetMostProductiveSeller() throws Exception {
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("DAY");
        filterRequest.setDate(LocalDate.of(2024, 10, 20));

        Seller mostProductiveSeller = new Seller();
        when(sellerService.getMostProductiveSeller(any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.ok(mostProductiveSeller));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(filterRequest);

        mockMvc.perform(post("/api/sellers/mostProductive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(sellerService, times(1)).getMostProductiveSeller(any(PeriodFilterDTO.class));
    }

    @Test
    void testGetSellersWithMinAmount() throws Exception {
        Long amount = 1000L; // Минимальная сумма
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("DAY");
        filterRequest.setDate(LocalDate.of(2024, 10, 20));

        Seller seller1 = new Seller();
        seller1.setName("Продавец 1");
        seller1.setContactInformation("info1@example.com");

        Seller seller2 = new Seller();
        seller2.setName("Продавец 2");
        seller2.setContactInformation("info2@example.com");

        List<Seller> sellersWithMinAmount = List.of(seller1, seller2);

        when(sellerService.getSellersWithMinAmount(eq(amount), any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.ok(sellersWithMinAmount));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(filterRequest);

        mockMvc.perform(post("/api/sellers/minAmount/{amount}", amount)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(sellerService, times(1)).getSellersWithMinAmount(eq(amount), any(PeriodFilterDTO.class));
    }

    @Test
    void testGetMostProductiveSellerForMonth() throws Exception {
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("MONTH");
        filterRequest.setDate(LocalDate.of(2024, 10, 1));

        Seller mostProductiveSeller = new Seller();
        when(sellerService.getMostProductiveSeller(any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.ok(mostProductiveSeller));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(filterRequest);

        mockMvc.perform(post("/api/sellers/mostProductive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(sellerService, times(1)).getMostProductiveSeller(any(PeriodFilterDTO.class));
    }

    @Test
    void testGetMostProductiveSellerForQuarter() throws Exception {
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("QUARTER");
        filterRequest.setDate(LocalDate.of(2024, 10, 20));

        Seller mostProductiveSeller = new Seller();
        when(sellerService.getMostProductiveSeller(any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.ok(mostProductiveSeller));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(filterRequest);

        mockMvc.perform(post("/api/sellers/mostProductive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(mostProductiveSeller.getName()));

        verify(sellerService, times(1)).getMostProductiveSeller(any(PeriodFilterDTO.class));
    }

    @Test
    void testGetMostProductiveSellerForYear() throws Exception {
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("YEAR");
        filterRequest.setDate(LocalDate.of(2024, 1, 1));

        Seller mostProductiveSeller = new Seller();
        mostProductiveSeller.setName("Most Productive Seller");

        when(sellerService.getMostProductiveSeller(any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.ok(mostProductiveSeller));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(filterRequest);

        mockMvc.perform(post("/api/sellers/mostProductive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Most Productive Seller"));

        verify(sellerService, times(1)).getMostProductiveSeller(any(PeriodFilterDTO.class));
    }

    @Test
    void testGetMostProductiveSellerNoSellersFound() throws Exception {
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("DAY");
        filterRequest.setDate(LocalDate.of(2024, 10, 20));

        when(sellerService.getMostProductiveSeller(any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.ok(null));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(filterRequest);

        mockMvc.perform(post("/api/sellers/mostProductive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().string(isEmptyOrNullString()));

        verify(sellerService, times(1)).getMostProductiveSeller(any(PeriodFilterDTO.class));
    }

    @Test
    void testGetMostProductiveSellerInvalidPeriodType() throws Exception {
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("INVALID_PERIOD"); // Неверный период
        filterRequest.setDate(LocalDate.of(2024, 10, 20));

        when(sellerService.getMostProductiveSeller(any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.badRequest().body(null));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(filterRequest);

        mockMvc.perform(post("/api/sellers/mostProductive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());

        verify(sellerService, times(1)).getMostProductiveSeller(any(PeriodFilterDTO.class));
    }

    @Test
    void testGetSellersWithMinAmountEmptyResult() throws Exception {
        Long amount = 1000L;
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("DAY");
        filterRequest.setDate(LocalDate.of(2024, 10, 20));

        when(sellerService.getSellersWithMinAmount(eq(amount), any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.ok(List.of()));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(filterRequest);

        mockMvc.perform(post("/api/sellers/minAmount/{amount}", amount)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(sellerService, times(1)).getSellersWithMinAmount(eq(amount), any(PeriodFilterDTO.class));
    }

    @Test
    void testGetSellersWithMinAmountValidResponse() throws Exception {
        Long amount = 1000L;
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("DAY");
        filterRequest.setDate(LocalDate.of(2024, 10, 20));

        // Создаем тестовых продавцов
        Seller seller1 = new Seller();
        seller1.setName("Продавец 1");
        seller1.setContactInformation("info1@example.com");

        Seller seller2 = new Seller();
        seller2.setName("Продавец 2");
        seller2.setContactInformation("info2@example.com");

        List<Seller> sellersWithMinAmount = List.of(seller1);

        when(sellerService.getSellersWithMinAmount(eq(amount), any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.ok(sellersWithMinAmount));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(filterRequest);

        mockMvc.perform(post("/api/sellers/minAmount/{amount}", amount)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Продавец 1"));

        verify(sellerService, times(1)).getSellersWithMinAmount(eq(amount), any(PeriodFilterDTO.class));
    }

    @Test
    void testGetSellersWithMinAmountLessThanExpected() throws Exception {
        Long amount = 1000L;
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("DAY");
        filterRequest.setDate(LocalDate.of(2024, 10, 20));

        Seller seller1 = new Seller();
        seller1.setName("Продавец 1");
        seller1.setContactInformation("info1@example.com");

        List<Seller> sellersWithMinAmount = List.of(seller1);

        when(sellerService.getSellersWithMinAmount(eq(amount), any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.ok(sellersWithMinAmount));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(filterRequest);

        mockMvc.perform(post("/api/sellers/minAmount/{amount}", amount)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Продавец 1"));

        verify(sellerService, times(1)).getSellersWithMinAmount(eq(amount), any(PeriodFilterDTO.class));
    }

    @Test
    void testGetSellersWithMinAmountEmptyList() throws Exception {
        Long amount = 1000L;
        PeriodFilterDTO filterRequest = new PeriodFilterDTO();
        filterRequest.setPeriodType("DAY");
        filterRequest.setDate(LocalDate.of(2024, 10, 20));

        when(sellerService.getSellersWithMinAmount(eq(amount), any(PeriodFilterDTO.class)))
                .thenReturn(ResponseEntity.ok(List.of()));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonContent = objectMapper.writeValueAsString(filterRequest);

        mockMvc.perform(post("/api/sellers/minAmount/{amount}", amount)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(sellerService, times(1)).getSellersWithMinAmount(eq(amount), any(PeriodFilterDTO.class));
    }

    @Test
    void testGetAllSellers_EmptyList() {
        when(sellerRepository.findAll()).thenReturn(Arrays.asList());

        List<Seller> result = sellerService.getAllSellers();

        assertEquals(0, result.size());
    }

    @Test
    void testHandleQuarter_FirstQuarter() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        sellerService.handleQuarter(date);
    }

    @Test
    void testHandleQuarter_SecondQuarter() {
        LocalDate date = LocalDate.of(2024, 4, 10);
        sellerService.handleQuarter(date);
    }

    @Test
    void testHandleQuarter_ThirdQuarter() {
        LocalDate date = LocalDate.of(2024, 7, 25);
        sellerService.handleQuarter(date);
    }

    @Test
    void testHandleQuarter_FourthQuarter() {
        LocalDate date = LocalDate.of(2024, 10, 5);
        sellerService.handleQuarter(date);
    }

    @Test
    void testHandleQuarter_EndOfYear() {
        LocalDate date = LocalDate.of(2024, 12, 31);
        sellerService.handleQuarter(date);
    }

    @Test
    void testHandleQuarter_StartOfYear() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        sellerService.handleQuarter(date);
    }
}

