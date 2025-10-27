package org.example.user_service.repository;

import org.example.user_service.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RepositoryTest {

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
    void create_correctUser() {
        User actualUser = new User("Nik", "fefe@mail.ru", 10, LocalDateTime.now());
        User user = userRepository.save(actualUser);
        assertEquals(user, actualUser);
    }

    @Test
    void create_nullUser() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> userRepository.save(null));
    }

    @Test
    void find_existUser() {
        User actualUser = new User("Nik", "fefe@mail.ru", 10,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        userRepository.save(actualUser);
        User expectedUser = userRepository.findById(actualUser.getId()).get();
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void find_notExistUser() {
        userRepository.save(new User("Nik", "fefe@mail.ru", 10, LocalDateTime.now()));
        assertEquals(userRepository.findById(99L), Optional.empty());
    }

    @Test
    void find_incorrectId() {
        assertThrows(NumberFormatException.class, () -> userRepository.findById(Long.parseLong("fcwefw")));
    }

    @Test
    void find_nullId() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> userRepository.findById((Long) null));
    }

    @Test
    void update_updateAgeUser() {
        User user = userRepository.save(new User("Nik", "fefe@mail.ru", 10, LocalDateTime.now()));
        User actualUser = userRepository.findById(user.getId()).get();
        actualUser.setAge(22);
        assertEquals(userRepository.save(actualUser), actualUser);
    }

    @Test
    void update_updateAllUser() {
        User user = userRepository.save(new User("Nik", "fefe@mail.ru", 10, LocalDateTime.now()));
        User actualUser = userRepository.findById(user.getId()).get();
        actualUser.setAge(22);
        actualUser.setName("lolol");
        actualUser.setName("errt@mail.ru");
        assertEquals(userRepository.save(actualUser), actualUser);
    }

    @Test
    void update_nullUser() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> userRepository.save(null));
    }

    @Test
    void delete_existUser() {
        User user = userRepository.save(new User("Nik", "fefe@mail.ru", 10, LocalDateTime.now()));
        userRepository.delete(user);
        assertEquals(userRepository.findById(user.getId()), Optional.empty());
    }

    @Test
    void delete_notExistUser() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> userRepository.delete(null));
    }
}
