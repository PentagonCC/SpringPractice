package org.example.notification_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.notification_service.dto.Message;
import org.example.notification_service.dto.MessageStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationSenderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void sendMessage_correctCreatedTest() throws Exception {
        Message message = new Message("keks3_3@mail.ru", MessageStatus.CREATED);

        mockMvc.perform(get("/notification/send")
                        .content(objectMapper.writeValueAsString(message))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void sendMessage_correctDeletedTests() throws Exception {
        Message message = new Message("keks3_3@mail.ru", MessageStatus.DELETED);

        mockMvc.perform(get("/notification/send")
                        .content(objectMapper.writeValueAsString(message))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void sendMessage_incorrectStatusMessage() throws Exception {
        Message message = new Message("keks3_3@mail.ru", null);

        mockMvc.perform(get("/notification/send")
                        .content(objectMapper.writeValueAsString(message))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sendMessage_incorrectEmailAddress() throws Exception {
        Message message = new Message("defe", MessageStatus.CREATED);

        mockMvc.perform(get("/notification/send")
                        .content(objectMapper.writeValueAsString(message))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
