package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.user.model.User;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<User> getAll() {
        log.info("Запрос на получение всех пользователей");
        return repository.findAll();
    }

    @Override
    public User get(Long id) throws ObjectNotFoundException {
        log.info("Запрос на получение пользователя с id - {}", id);
        return repository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    @Transactional
    public User save(User user) {
        log.info("Запрос на обновление пользователя - {}", user.getEmail());
        return repository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) throws ObjectNotFoundException {
        log.info("Запрос на удаление пользователя с id - {}", id);
        repository.delete(get(id));
    }
}
