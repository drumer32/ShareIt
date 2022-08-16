package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.practicum.shareit.ModelsRepForTests.*;

@Transactional
@SpringBootTest
public class UserServiceTest {

    private final UserRepository userRepository;
    private final UserService userService;


    @Autowired
    public UserServiceTest(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
        userRepository.save(user);
        userRepository.save(user2);
    }

    @Test
    void testSave() {
        userService.save(user3);
        assertEquals(user3, userService.get(user3.getId()));
    }

    @Test
    void testGetAll() {
        assertEquals(List.of(user, user2), userService.getAll());
    }

    @Test
    void testGet() {
        assertEquals(user, userService.get(user.getId()));
    }

    @Test
    void testDelete() {
        userService.delete(user.getId());
        assertNull(userRepository.findById(user.getId()).orElse(null));
    }
}
