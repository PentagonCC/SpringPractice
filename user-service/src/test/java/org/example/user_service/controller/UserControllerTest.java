package org.example.user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.user_service.dto.UserDTO;
import org.example.user_service.model.User;
import org.example.user_service.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("testDB")
            .withUsername("testName")
            .withPassword("testPass");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @BeforeAll
    public static void setUp() {
        postgres.start();
    }

    @AfterAll
    public static void setDown() {
        postgres.stop();
    }

    @AfterEach
    public void clearDB() {
        userRepository.deleteAll();
    }

    @Test
    void createUser_correctUser() throws Exception {

        User user = new User("fefel", "dwff@mail.ru", 11, LocalDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES));
        UserDTO userDTO = new UserDTO().convertToDTO(user);

        mockMvc.perform(post("/users/create")
                        .content(objectMapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("fefel"))
                .andExpect(jsonPath("$.email").value("dwff@mail.ru"))
                .andExpect(jsonPath("$.age").value(11));
    }

    @Test
    void createUser_incorrectUser() throws Exception {
        User user = new User("  ", "dwff@mail.ru", 11, LocalDateTime.now()
                .truncatedTo(ChronoUnit.MINUTES));
        UserDTO userDTO = new UserDTO().convertToDTO(user);

        mockMvc.perform(post("/users/create")
                        .content(objectMapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void getUserById_userFound() throws Exception {
        User user = new User("dwdw", "ddwWw@mail.ru", 22,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        User testUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO().convertToDTO(testUser);

        mockMvc.perform(get("/users/{id}", userDTO.getId())
                        .content(objectMapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDTO.getId()))
                .andExpect(jsonPath("$.name").value("dwdw"))
                .andExpect(jsonPath("$.email").value("ddwWw@mail.ru"))
                .andExpect(jsonPath("$.age").value(22));
    }

    @Test
    void getUserById_userNotFound() throws Exception {
        Long notFoundId = 3443L;
        mockMvc.perform(get("/users/{id}", notFoundId))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_correctData() throws Exception {
        User user = new User("dwdw", "ddwWw@mail.ru", 22,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        userRepository.save(user);
        User userWithNewData = new User("dw32", "ddwWw@mail.ru", 28,
                user.getCreatedAt());

        mockMvc.perform(put("/users/{id}/update", user.getId())
                        .content(objectMapper.writeValueAsString(userWithNewData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value("dw32"))
                .andExpect(jsonPath("$.email").value("ddwWw@mail.ru"))
                .andExpect(jsonPath("$.age").value(28));
    }

    @Test
    void updateUser_incorrectData() throws Exception {
        User user = new User("dwdw", "ddwWw@mail.ru", 22,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        userRepository.save(user);
        User incorrectUser = new User(" ", "ddwWw@mail.ru", 28,
                user.getCreatedAt());
        mockMvc.perform(put("/users/{id}/update", user.getId())
                        .content(objectMapper.writeValueAsString(incorrectUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser_nullUser() throws Exception {
        User user = new User("dwdw", "ddwWw@mail.ru", 22,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        userRepository.save(user);
        mockMvc.perform(put("/users/{id}/update", user.getId())
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void deleteUser_foundUser() throws Exception {
        User user = new User("dwdw", "ddwWw@mail.ru", 22,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        userRepository.save(user);

        mockMvc.perform(delete("/users/{id}/delete", user.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_notFoundUser() throws Exception {
        mockMvc.perform(delete("/users/{id}/delete", 3243))
                .andExpect(status().isNoContent());
    }


}
