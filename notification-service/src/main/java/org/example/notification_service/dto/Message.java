package org.example.notification_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Message {
    @Email
    @NotBlank
    private String userEmail;
    @NotNull
    private MessageStatus status;

    public Message(String userEmail, MessageStatus status) {
        this.userEmail = userEmail;
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public MessageStatus getStatus() {
        return status;
    }
}
