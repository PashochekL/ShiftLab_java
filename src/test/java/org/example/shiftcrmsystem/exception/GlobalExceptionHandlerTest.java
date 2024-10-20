package org.example.shiftcrmsystem.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Illegal argument error");

        ResponseEntity<String> response = exceptionHandler.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Illegal argument error", response.getBody());
    }

    @Test
    void testHandleEntityNotFoundException() {
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");

        ResponseEntity<String> response = exceptionHandler.handleEntityNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Entity not found", response.getBody());
    }

    @Test
    void testHandleDataIntegrityViolationException() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Data integrity violation error");

        ResponseEntity<String> response = exceptionHandler.handleDataIntegrityViolationException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Data integrity violation: Data integrity violation error", response.getBody());
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Generic error");

        ResponseEntity<String> response = exceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred: Generic error", response.getBody());
    }

    @Test
    void testHandleTransactionNotFoundException() {
        TransactionNotFoundException exception = new TransactionNotFoundException("Transaction not found");

        ResponseEntity<String> response = exceptionHandler.handleTransactionNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Transaction not found", response.getBody());
    }
}
