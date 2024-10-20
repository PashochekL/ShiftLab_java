package org.example.shiftcrmsystem.entities;

import org.example.shiftcrmsystem.DTO.SellerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SellerTest {

    private SellerDTO sellerDTO;

    @BeforeEach
    public void setUp() {
        sellerDTO = new SellerDTO();
        sellerDTO.setName("Test Seller");
        sellerDTO.setContactInformation("contact@example.com");
    }

    @Test
    public void testSellerConstructor() {
        Seller seller = new Seller(sellerDTO);

        assertNotNull(seller);
        assertEquals("Test Seller", seller.getName());
        assertEquals("contact@example.com", seller.getContactInformation());
        assertNotNull(seller.getRegistrationDate());
        assertNull(seller.getId());
        assertNull(seller.getTransactions());
    }

    @Test
    public void testToString() {
        Seller seller = new Seller(sellerDTO);

        String expectedString = "Seller{id=null, name='Test Seller', contactInformation='contact@example.com', transactions=0}";

        assertEquals(expectedString, seller.toString());
    }
}
