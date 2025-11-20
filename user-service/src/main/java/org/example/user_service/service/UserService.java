package org.example.user_service.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.user_service.dto.Message;
import org.example.user_service.dto.MessageStatus;
import org.example.user_service.dto.UserDTO;
import org.example.user_service.model.User;
import org.example.user_service.producer.NotificationProducer;
import org.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class UserService {
    private final NotificationProducer notificationProducer;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);

    }

    @CircuitBreaker(name = "serviceBreaker", fallbackMethod = "fallBackSendCreateNotification")
    public UserDTO createUser(User user) {
        if (isCorrectUser(user)) {
            User newUser = userRepository.save(new User(user.getName(), user.getEmail(), user.getAge(),
                    LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)));
            UserDTO newUserDTO = new UserDTO().convertToDTO(newUser);
            newUserDTO.setStatus("Пользователь успешно создан");
            notificationProducer.sendNotification(new Message(newUser.getEmail(), MessageStatus.CREATED));
            return newUserDTO;
        }
        return new UserDTO();
    }

    public UserDTO fallBackSendCreateNotification(User user, Throwable t) {
        UserDTO newUserDTO = new UserDTO().convertToDTO(user);
        newUserDTO.setStatus("Пользователь создан! К сожалению сервис отправки уведомлений не доступен, уведомление будет выслано позже");
        return newUserDTO;
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
        notificationProducer.sendNotification(new Message(user.getEmail(), MessageStatus.DELETED));
    }

    public User updateUser(Long id, User updatedUser) {
        if (isCorrectUser(updatedUser)) {
            Optional<User> foundUser = userRepository.findById(id);
            if (foundUser.isPresent()) {
                User user = foundUser.get();
                user.setAge(updatedUser.getAge());
                user.setEmail(updatedUser.getEmail());
                user.setName(updatedUser.getName());
                userRepository.save(user);
                return user;
            }
        }
        return new User();
    }

    public static boolean isCorrectUser(User user) {
        if (user != null) {
            return user.getName() != null && user.getAge() > 0 && user.getEmail() != null
                    && !user.getName().trim().isBlank() && !user.getEmail().trim().isBlank();

        }
        return false;
    }
}
