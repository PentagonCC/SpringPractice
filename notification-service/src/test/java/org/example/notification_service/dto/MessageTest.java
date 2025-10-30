package org.example.notification_service.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {

    @Test
    void getter_getEmail() {
        Message message = new Message("fewf@mail.ru", MessageStatus.CREATED);

        assertEquals("fewf@mail.ru", message.getUserEmail());
    }

    @Test
    void getter_getStatus() {
        Message message = new Message("fewf@mail.ru", MessageStatus.CREATED);

        assertEquals("CREATED", message.getStatus().toString());
    }
}
