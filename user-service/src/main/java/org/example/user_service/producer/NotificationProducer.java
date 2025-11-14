package org.example.user_service.producer;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.user_service.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private static final Logger log = LoggerFactory.getLogger(NotificationProducer.class);
    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    public NotificationProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Message message) {
        kafkaTemplate.send("notifications", message);
    }
}
