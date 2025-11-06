package org.example.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.user_service.model.User;

@Schema(description = "Cущность пользователя, возвращаемая из методов контроллера")
public class UserDTO {

    @Schema(description = "Уникальный идентификатор")
    private Long id;
    @Schema(description = "Имя пользователя")
    private String name;
    @Schema(description = "Почта пользователя")
    private String email;
    @Schema(description = "Возраст пользователя")
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAge(user.getAge());
        return userDTO;
    }
}
