package org.example.user_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.user_service.dto.UserDTO;
import org.example.user_service.model.User;
import org.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Tag(name = "Контроллер управления пользователями", description = "Позволяет выполнять CRUD операции над пользователями")
@Validated
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Поиск пользователя по его идентификатору")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable @Parameter(description = "Уникальный идентификатор") Long id) {
        Optional<User> foundUser = userService.getUserById(id);
        ResponseEntity<UserDTO> response;
        if(foundUser.isPresent()) {
            User user = foundUser.get();
            UserDTO userDTO = new UserDTO().convertToDTO(user);
            userDTO.add(linkTo(UserController.class).slash("swagger-ui/index.html#/").withSelfRel());
            response = ResponseEntity.ok().body(userDTO);
        }else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Operation(summary = "Создание пользователя")
    @PostMapping("/users/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid User newUser) {
        User createdUser = userService.createUser(newUser);
        UserDTO userDTO = new UserDTO().convertToDTO(createdUser);
        userDTO.add(linkTo(UserController.class).slash("users").slash(userDTO.getId()).withSelfRel());

        return ResponseEntity.status(201).body(userDTO);
    }

    @Operation(summary = "Удаление пользователя", description = "Позволяет удалить пользователя по его идентификатору")
    @DeleteMapping("/users/{id}/delete")
    public void deleteUser(@PathVariable @Parameter(description = "Уникальный идентификатор") Long id) {
        Optional<User> foundUser = userService.getUserById(id);
        foundUser.ifPresent(userService::deleteUser);
    }

    @Operation(summary = "Обновление пользователя",
            description = "Позволяет обновить пользователя целиком по его идентификатору")
    @PutMapping("/users/{id}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable @Parameter(description = "Уникальный идентификатор") Long id,
                                              @RequestBody @Valid User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        UserDTO userDTO = new UserDTO().convertToDTO(user);
        userDTO.add(linkTo(UserController.class).slash("users").slash(userDTO.getId()).withSelfRel());
        return ResponseEntity.ok().body(userDTO);
    }
}
