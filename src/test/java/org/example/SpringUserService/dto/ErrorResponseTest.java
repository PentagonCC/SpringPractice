package org.example.SpringUserService.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ErrorResponseTest {

    @Test
    void getter_getMessage() {
        ErrorResponse errorResponse = new ErrorResponse("error", "message", "OK");
        assertEquals("message", errorResponse.getMessage());
        assertNotEquals("error", errorResponse.getMessage());
    }

    @Test
    void getter_getError() {
        ErrorResponse errorResponse = new ErrorResponse("error", "message", "OK");
        assertEquals("error", errorResponse.getError());
        assertNotEquals("message", errorResponse.getError());
    }

    @Test
    void getter_getStatus() {
        ErrorResponse errorResponse = new ErrorResponse("error", "message", "OK");
        assertEquals("OK", errorResponse.getStatus());
        assertNotEquals("BAD", errorResponse.getStatus());
    }
}
