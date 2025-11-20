package org.example.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.user_service.model.User;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Schema(description = "Cущность пользователя, возвращаемая из методов контроллера")
public class UserDTO extends RepresentationModel<UserDTO> {

    @Schema(description = "Уникальный идентификатор")
    private Long id;
    @Schema(description = "Имя пользователя")
    private String name;
    @Schema(description = "Почта пользователя")
    private String email;
    @Schema(description = "Возраст пользователя")
    private int age;
    @Schema(description = "Статус операции")
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAge(user.getAge());
        return userDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDTO userDTO = (UserDTO) o;
        return age == userDTO.age && Objects.equals(id, userDTO.id) && Objects.equals(name, userDTO.name) &&
                Objects.equals(email, userDTO.email) && Objects.equals(status, userDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, email, age, status);
    }
}
