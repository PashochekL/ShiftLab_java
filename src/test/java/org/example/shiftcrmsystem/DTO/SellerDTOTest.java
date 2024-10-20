package org.example.shiftcrmsystem.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SellerDTOTest {

    @Test
    void testConstructorWithParameters() {
        String name = "Test Seller";
        String contactInfo = "test@example.com";

        SellerDTO sellerDTO = new SellerDTO(name, contactInfo);

        assertEquals(name, sellerDTO.getName());
        assertEquals(contactInfo, sellerDTO.getContactInformation());
    }

    @Test
    void testDefaultConstructor() {
        SellerDTO sellerDTO = new SellerDTO();

        assertEquals(null, sellerDTO.getName());
        assertEquals(null, sellerDTO.getContactInformation());
    }

    @Test
    void testSettersAndGetters() {
        SellerDTO sellerDTO = new SellerDTO();

        String name = "New Seller";
        String contactInfo = "new@example.com";

        sellerDTO.setName(name);
        sellerDTO.setContactInformation(contactInfo);

        assertEquals(name, sellerDTO.getName());
        assertEquals(contactInfo, sellerDTO.getContactInformation());
    }

    @Test
    void testEmptyFields() {
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setName("");
        sellerDTO.setContactInformation("");

        assertEquals("", sellerDTO.getName());
        assertEquals("", sellerDTO.getContactInformation());
    }

    @Test
    void testNullFields() {
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setName(null);
        sellerDTO.setContactInformation(null);

        assertEquals(null, sellerDTO.getName());
        assertEquals(null, sellerDTO.getContactInformation());
    }

    @Test
    void testEqualsAndHashCode() {
        SellerDTO sellerDTO1 = new SellerDTO("Seller A", "sellerA@example.com");
        SellerDTO sellerDTO2 = new SellerDTO("Seller A", "sellerA@example.com");
        SellerDTO sellerDTO3 = new SellerDTO("Seller B", "sellerB@example.com");

        assertEquals(sellerDTO1, sellerDTO2);
        assertNotEquals(sellerDTO1, sellerDTO3);
        assertEquals(sellerDTO1.hashCode(), sellerDTO2.hashCode());
    }

    @Test
    void testToString() {
        SellerDTO sellerDTO = new SellerDTO("Seller", "seller@example.com");
        String expectedString = "SellerDTO(name=Seller, contactInformation=seller@example.com)";
        assertEquals(expectedString, sellerDTO.toString());
    }
}
