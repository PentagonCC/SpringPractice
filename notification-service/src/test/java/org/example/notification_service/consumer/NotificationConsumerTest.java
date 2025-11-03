package org.example.notification_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.notification_service.dto.Message;
import org.example.notification_service.service.NotificationSenderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationConsumerTest {

    @Mock
    private NotificationSenderService notificationSenderService;

    @InjectMocks
    private NotificationConsumer notificationConsumer;

    @Test
    void consumeNotification_created() throws JsonProcessingException {
        String jsonMessage = "{\"userEmail\":\"fefe@fef.com\",\"status\":\"CREATED\"}";
        notificationConsumer.consumeNotification(jsonMessage);
        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(notificationSenderService).sendEmailNotification(argumentCaptor.capture());
        Message testMessage = argumentCaptor.getValue();
        assertEquals("fefe@fef.com", testMessage.getUserEmail());
        assertEquals("CREATED", testMessage.getStatus().toString());
    }

    @Test
    void consumeNotification_emptyMessage() {
        String emptyMessage = "";
        assertThrows(JsonProcessingException.class,
                () -> notificationConsumer.consumeNotification(emptyMessage));
    }

    @Test
    void consumeNotification_nullMessage() {
        assertThrows(IllegalArgumentException.class,
                () -> notificationConsumer.consumeNotification(null));
    }

    @Test
    void consumeNotification_correctJsonWithDeleted() throws JsonProcessingException {
        String jsonMessage = "{\"userEmail\":\"fefe@fef.com\",\"status\":\"DELETED\"}";
        notificationConsumer.consumeNotification(jsonMessage);
        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(notificationSenderService).sendEmailNotification(argumentCaptor.capture());
        Message testMessage = argumentCaptor.getValue();
        assertEquals("fefe@fef.com", testMessage.getUserEmail());
        assertEquals("DELETED", testMessage.getStatus().toString());
    }

    @Test
    void consumeNotification_incorrectJson() {
        assertThrows(JsonProcessingException.class, () -> notificationConsumer.consumeNotification("invalid message"));
        verify(notificationSenderService, never()).sendEmailNotification(any(Message.class));
    }
}
