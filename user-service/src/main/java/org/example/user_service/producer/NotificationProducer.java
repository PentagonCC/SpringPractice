package org.example.user_service.producer;

import org.example.user_service.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    public NotificationProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Message message) {
        kafkaTemplate.send("notifications", message);
    }
}
