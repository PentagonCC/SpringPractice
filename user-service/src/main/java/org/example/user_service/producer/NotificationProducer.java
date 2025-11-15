package org.example.user_service.producer;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.user_service.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class NotificationProducer {

    private static final Logger log = LoggerFactory.getLogger(NotificationProducer.class);
    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    public NotificationProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @CircuitBreaker(name = "serviceBreaker", fallbackMethod = "fallBackSendNotification")
    public void sendNotification(Message message) {
        try {
            kafkaTemplate.send("notifications", message).get(3, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void fallBackSendNotification(Message message, Throwable t) {
        log.info("Не удалось отправить уведомление. Уведомление будет отправлено позже.");
    }
}
