package ru.practicum.shareitserver.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareitserver.exceptions.ObjectNotFoundException;
import ru.practicum.shareitserver.user.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.shareitserver.ModelsRepForTests.*;

@Transactional
@SpringBootTest
public class UserServiceTest {

    private final UserService userService;

    @Autowired
    UserServiceTest(UserService userService) {
        this.userService = userService;
        userService.save(user);
        userService.save(user2);
    }

    @Test
    void testSave() throws ObjectNotFoundException {
        userService.save(user3);
        assertEquals(user3, userService.get(user3.getId()));
    }

    @Test
    void testGetAll() {
        assertEquals(List.of(user, user2), userService.getAll());
    }

    @Test
    void testGet() throws ObjectNotFoundException {
        assertEquals(user, userService.get(user.getId()));
    }

    @Test
    void testDelete() throws ObjectNotFoundException {
        userService.delete(user.getId());
        assertThrows(ObjectNotFoundException.class, () -> userService.get(user.getId()));
    }
}
