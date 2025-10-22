package org.example.SpringUserService.controller;

import org.example.SpringUserService.dto.UserDTO;
import org.example.SpringUserService.model.User;
import org.example.SpringUserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        User foundUser = userService.getUserById(id);
        return new UserDTO().convertToDTO(foundUser);
    }

    @PostMapping("/users/create")
    public UserDTO createUser(@RequestBody User newUser) {
        User createdUser = userService.createUser(newUser);
        return new UserDTO().convertToDTO(createdUser);
    }

    @DeleteMapping("/users/{id}/delete")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(userService.getUserById(id));
    }

    @PutMapping("/users/{id}/update")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return new UserDTO().convertToDTO(user);
    }
}
