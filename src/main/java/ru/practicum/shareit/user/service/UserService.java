package ru.practicum.shareit.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
public interface UserService {

    List<User> getAll();

    User get(long id);

    User save(User user);

    void delete(long id);
}
