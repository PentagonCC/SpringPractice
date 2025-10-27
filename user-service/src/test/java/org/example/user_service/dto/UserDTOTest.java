package org.example.user_service.dto;

import org.example.user_service.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserDTOTest {

    @Test
    void getter_getId() {
        User user = new User("dcece", "23dew@mail.ru", 34, LocalDateTime.now());
        UserDTO userDTO = new UserDTO().convertToDTO(user);
        assertEquals(null, userDTO.getId());
        assertNotEquals(32, userDTO.getId());
    }

    @Test
    void getter_getName() {
        User user = new User("dcece", "23dew@mail.ru", 34, LocalDateTime.now());
        UserDTO userDTO = new UserDTO().convertToDTO(user);
        assertEquals("dcece", userDTO.getName());
        assertNotEquals("fefe", userDTO.getName());
    }

    @Test
    void getter_getEmail() {
        User user = new User("dcece", "23dew@mail.ru", 34, LocalDateTime.now());
        UserDTO userDTO = new UserDTO().convertToDTO(user);
        assertEquals("23dew@mail.ru", userDTO.getEmail());
        assertNotEquals("wgvf@mail.ru", userDTO.getName());
    }

    @Test
    void getter_getAge() {
        User user = new User("dcece", "23dew@mail.ru", 34, LocalDateTime.now());
        UserDTO userDTO = new UserDTO().convertToDTO(user);
        assertEquals(34, userDTO.getAge());
        assertNotEquals(55, userDTO.getAge());
    }

    @Test
    void setter_setId() {
        Long newId = 32L;
        User user = new User("dcece", "23dew@mail.ru", 34, LocalDateTime.now());
        UserDTO userDTO = new UserDTO().convertToDTO(user);
        userDTO.setId(newId);
        assertEquals(newId, userDTO.getId());
        assertNotEquals(user.getId(), userDTO.getId());
    }

    @Test
    void setter_setName() {
        String newName = "Nikita";
        User user = new User("dcece", "23dew@mail.ru", 34, LocalDateTime.now());
        UserDTO userDTO = new UserDTO().convertToDTO(user);
        userDTO.setName(newName);
        assertEquals(newName, userDTO.getName());
        assertNotEquals("dcece", userDTO.getName());
    }

    @Test
    void setter_setEmail() {
        String newEmail = "pol@mail.ru";
        User user = new User("dcece", "23dew@mail.ru", 34, LocalDateTime.now());
        UserDTO userDTO = new UserDTO().convertToDTO(user);
        userDTO.setEmail(newEmail);
        assertEquals(newEmail, userDTO.getEmail());
        assertNotEquals("23dew@mail.ru", userDTO.getEmail());
    }

    @Test
    void setter_setAge() {
        int newAge = 222;
        User user = new User("dcece", "23dew@mail.ru", 34, LocalDateTime.now());
        UserDTO userDTO = new UserDTO().convertToDTO(user);
        userDTO.setAge(newAge);
        assertEquals(newAge, userDTO.getAge());
        assertNotEquals(34, userDTO.getAge());
    }
}
