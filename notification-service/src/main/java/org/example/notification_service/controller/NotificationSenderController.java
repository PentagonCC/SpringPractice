package org.example.notification_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.notification_service.dto.Message;
import org.example.notification_service.service.NotificationSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@Tag(name = "Контроллер по отправке уведомлений", description = "Позволяет отправить уведомление пользователю на почту")
public class NotificationSenderController {

    private final NotificationSenderService notificationSenderService;

    @Autowired
    public NotificationSenderController(NotificationSenderService notificationSenderService) {
        this.notificationSenderService = notificationSenderService;
    }

    @Operation(summary = "Отправка уведомления")
    @GetMapping("/notification/send")
    public void sendMessage(@RequestBody @Valid Message message) {
        notificationSenderService.sendEmailNotification(message);
    }

}
