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
        UserDTO userDTO = new UserDTO();
        userDTO = userDTO.convertToDTO(foundUser);
        return userDTO;
    }

    @PostMapping("/users/create")
    public UserDTO createUser(@RequestBody User newUser) {
        User createdUser = userService.createUser(newUser);
        UserDTO userDTO = new UserDTO();
        userDTO = userDTO.convertToDTO(createdUser);
        return userDTO;
    }


}
