package org.example.user_service.service;

import org.example.user_service.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.example.user_service.service.UserService.isCorrectUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void isCorrectUser_nullUser() {
        assertFalse(isCorrectUser(null));
    }

    @Test
    void isCorrectUser_nullName() {
        User user = new User(null, "fwfewfwe@fe", 25,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        boolean result = isCorrectUser(user);
        assertFalse(result);
    }

    @Test
    void isCorrectUser_emptyName() {
        User user = new User("", "fwfewfwe@fe", 25,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        boolean result = isCorrectUser(user);
        assertFalse(result);
    }

    @Test
    void isCorrectUser_nullEmail() {
        User user = new User("fef", null, 25,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        boolean result = isCorrectUser(user);
        assertFalse(result);
    }

    @Test
    void isCorrectUser_blankEmail() {
        User user = new User("fef", "   ", 25,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        boolean result = isCorrectUser(user);
        assertFalse(result);
    }


    @Test
    void isCorrectUser_negativeAge() {
        User user = new User("fef", "fwfewfwe@fe", -5,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        boolean result = isCorrectUser(user);
        assertFalse(result);
    }

    @Test
    void isCorrectUser_spacesOnlyStringFields() {
        User user = new User("   ", "   ", 25,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        boolean result = isCorrectUser(user);
        assertFalse(result);
    }

    @Test
    void createUser_nullUser() {
        assertEquals(new User(), userService.createUser(null));
    }

    @Test
    void createUser_incorrectUser() {
        assertEquals(new User(), userService.createUser(new User("   ", "   ", 25,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))));
    }

    @Test
    void updateUser_nullUser() {
        assertEquals(new User(), userService.updateUser(1L, null));
    }

    @Test
    void updateUser_incorrectUser() {
        assertEquals(new User(), userService.updateUser(1L, new User("   ", "   ", 25,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))));
    }
}
