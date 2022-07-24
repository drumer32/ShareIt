package ru.practicum.shareit.user.model;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserDto {
    private String name;

    @Email
    private String email;
}
