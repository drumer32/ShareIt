package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    public User createUser(@Validated @RequestBody User user) {
        return userService.save(user);
    }

    @PatchMapping(value = {"/{id}"})
    public User updateUser(@Validated @RequestBody UserDto userDto, @PathVariable Long id) {
        User user = userService.get(id);
        modelMapper.map(userDto, user);
        return userService.save(user);
    }

    @DeleteMapping(value = {"/{id}"})
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping(value = {"/{id}"})
    public User getUserById(@PathVariable Long id) {
        return userService.get(id);
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAll();
    }
}
