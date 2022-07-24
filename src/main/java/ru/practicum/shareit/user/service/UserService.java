package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User get(long id);

    User save(User user);

    void delete(long id);
}
