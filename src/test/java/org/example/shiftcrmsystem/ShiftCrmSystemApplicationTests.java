package org.example.shiftcrmsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ShiftCrmSystemApplicationTest {

    @Test
    void contextLoads() {
        ShiftCrmSystemApplication.main(new String[] {});
    }

    @Test
    void main() {
        String[] args = {};
        ConfigurableApplicationContext context = SpringApplication.run(ShiftCrmSystemApplication.class, args);

        assertNotNull(context);

        context.close();
    }
}
