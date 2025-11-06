package org.example.notification_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Сообщение с данными для отправки уведомления")
public class Message {
    @Email
    @NotBlank
    @Schema(description = "Почта для отправки уведомления")
    private String userEmail;
    @NotNull
    @Schema(description = "Статус, используемый для выбора уведомления")
    private MessageStatus status;

    public Message() {
    }

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
