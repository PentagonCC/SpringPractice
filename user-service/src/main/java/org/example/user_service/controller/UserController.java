package org.example.user_service.controller;

import jakarta.validation.Valid;
import org.example.user_service.dto.UserDTO;
import org.example.user_service.model.User;
import org.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Validated
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> foundUser = userService.getUserById(id);
        return foundUser.map(user -> ResponseEntity.ok().body(new UserDTO().convertToDTO(user))).orElseGet(
                () -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid User newUser) {
        User createdUser = userService.createUser(newUser);
        return ResponseEntity.status(201).body(new UserDTO().convertToDTO(createdUser));
    }

    @DeleteMapping("/users/{id}/delete")
    public void deleteUser(@PathVariable Long id) {
        Optional<User> foundUser = userService.getUserById(id);
        foundUser.ifPresent(userService::deleteUser);
    }

    @PutMapping("/users/{id}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody @Valid User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok().body(new UserDTO().convertToDTO(user));
    }
}
