package org.example.notification_service.controller;

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
public class NotificationSenderController {

    private final NotificationSenderService notificationSenderService;

    @Autowired
    public NotificationSenderController(NotificationSenderService notificationSenderService) {
        this.notificationSenderService = notificationSenderService;
    }

    @GetMapping("/notification/send")
    public void sendMessage(@RequestBody @Valid Message message) {
        notificationSenderService.sendEmailNotification(message);
    }

}
