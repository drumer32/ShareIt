package ru.practicum.shareitgateway.user.validation;

import ru.practicum.shareitgateway.user.dto.UpdateUserDto;

public class UserUpdateValidator {

    public boolean isValid(UpdateUserDto user) {
        if (user.getEmail() == null && user.getName() == null) {
            return false;
        }

        if (user.getEmail() != null && user.getEmail().isBlank()) {
            return false;
        }

        if (user.getName() != null && user.getName().isBlank()) {
            return false;
        }

        return true;
    }
}
