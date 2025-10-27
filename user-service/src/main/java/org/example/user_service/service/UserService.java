package org.example.user_service.service;

import org.example.user_service.model.User;
import org.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);

    }

    public User createUser(User user) {
        if (isCorrectUser(user)) {
            return userRepository.save(new User(user.getName(), user.getEmail(), user.getAge(),
                    LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)));
        }
        return new User();
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
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
