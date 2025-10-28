package org.example.notification_service.handler;

import org.example.notification_service.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleInvalidArgument_test() {
        ErrorResponse errorResponse = new ErrorResponse("Invalid argument", "Check validity data",
                HttpStatus.BAD_REQUEST.toString());

        ResponseEntity<ErrorResponse> response = handler.handleInvalidArgument();

        assertNotNull(response);
        assertEquals(errorResponse.getStatus(), response.getStatusCode().toString());

    }

    @Test
    void handleException_test() {
        ErrorResponse errorResponse = new ErrorResponse("Internal server error", "Try again later",
                HttpStatus.INTERNAL_SERVER_ERROR.toString());

        ResponseEntity<ErrorResponse> response = handler.handleException();

        assertNotNull(response);
        assertEquals(errorResponse.getStatus(), response.getStatusCode().toString());
    }
}
