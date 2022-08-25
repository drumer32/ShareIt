package ru.practicum.shareitserver.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareitserver.exceptions.ObjectNotFoundException;
import ru.practicum.shareitserver.user.model.User;

import java.util.List;

@Service
public interface UserService {

    List<User> getAll();

    User get(Long id) throws ObjectNotFoundException;

    User save(User user);

    void delete(Long id) throws ObjectNotFoundException;
}
