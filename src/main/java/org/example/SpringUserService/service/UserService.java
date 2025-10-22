package org.example.SpringUserService.service;

import org.example.SpringUserService.model.User;
import org.example.SpringUserService.repository.UserRepository;
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

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseGet(User::new);

    }

    public User createUser(User user) {
        if (!user.getName().trim().isEmpty() && !user.getEmail().trim().isEmpty() && user.getAge() > 0) {
            return userRepository.save(new User(user.getName(), user.getEmail(), user.getAge(),
                    LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)));
        }
        return new User();
    }

}
