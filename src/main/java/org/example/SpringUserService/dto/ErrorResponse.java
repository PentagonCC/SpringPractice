package org.example.SpringUserService.dto;

public class ErrorResponse {
    private String error;
    private String message;
    private String status;

    public ErrorResponse(String error, String message, String status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

}
