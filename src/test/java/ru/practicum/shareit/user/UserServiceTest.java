package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional
@SpringBootTest
public class UserServiceTest {

    private final UserRepository userRepository;
    private final UserService userService;
    private User user = new User(1L, "test", "test@gmail.com");
    private User user2 = new User(2L, "test2", "test2@gmail.com");

    @Autowired
    public UserServiceTest(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
        userRepository.save(user);
        userRepository.save(user2);
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

    @Test
    void testSave() {
        userService.save(user);
        assertEquals(user, userService.get(user.getId()));
    }
}
