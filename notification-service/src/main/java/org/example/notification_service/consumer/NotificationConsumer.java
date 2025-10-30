package org.example.notification_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.notification_service.dto.Message;
import org.example.notification_service.service.NotificationSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private final NotificationSenderService notificationSenderService;

    @Autowired
    public NotificationConsumer(NotificationSenderService notificationSenderService) {
        this.notificationSenderService = notificationSenderService;
    }

    @KafkaListener(topics = "notifications", groupId = "notificationService")
    public void consumeNotification(String jsonMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = objectMapper.readValue(jsonMessage, Message.class);
        notificationSenderService.sendEmailNotification(message);
    }
}
