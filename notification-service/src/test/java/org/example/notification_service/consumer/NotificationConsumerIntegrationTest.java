package org.example.notification_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.notification_service.dto.Message;
import org.example.notification_service.dto.MessageStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, topics = {"notifications"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NotificationConsumerIntegrationTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockitoSpyBean
    private NotificationConsumer notificationConsumer;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void messageWithCreatedStatus() throws JsonProcessingException {
        Message message = new Message("testuser@example.com", MessageStatus.CREATED);
        String jsonMessage = objectMapper.writeValueAsString(message);

        kafkaTemplate.send("notifications", jsonMessage);
        kafkaTemplate.flush();

        verify(notificationConsumer, timeout(5000).times(1)).consumeNotification(jsonMessage);
    }

    @Test
    void messageWithDeleteStatus() throws JsonProcessingException {
        Message message = new Message("deleteduser@example.com", MessageStatus.DELETED);
        String jsonMessage = objectMapper.writeValueAsString(message);

        kafkaTemplate.send("notifications", jsonMessage);
        kafkaTemplate.flush();

        verify(notificationConsumer, timeout(5000).times(1)).consumeNotification(jsonMessage);
    }

    @Test
    void multipleMessage() throws JsonProcessingException {
        Message message1 = new Message("test1@example.com", MessageStatus.CREATED);
        Message message2 = new Message("test2@example.com", MessageStatus.DELETED);

        String jsonMessage1 = objectMapper.writeValueAsString(message1);
        String jsonMessage2 = objectMapper.writeValueAsString(message2);

        kafkaTemplate.send("notifications", jsonMessage1);
        kafkaTemplate.flush();
        kafkaTemplate.send("notifications", jsonMessage2);
        kafkaTemplate.flush();

        verify(notificationConsumer, timeout(5000).times(2))
                .consumeNotification(any(String.class));
    }
}