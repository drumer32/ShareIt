package ru.practicum.shareitserver.requests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareitserver.exceptions.ObjectNotFoundException;
import ru.practicum.shareitserver.requests.repository.ItemRequestRepository;
import ru.practicum.shareitserver.requests.service.ItemRequestService;
import ru.practicum.shareitserver.user.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.practicum.shareitserver.ModelsRepForTests.*;

@SpringBootTest
public class RequestServiceTest {

    ItemRequestService requestService;

    ItemRequestRepository repository;

    UserService userService;

    @Autowired
    public RequestServiceTest(ItemRequestRepository repository,
                              ItemRequestService requestService,
                              UserService userService) {
        this.repository = repository;
        this.requestService = requestService;
        this.userService = userService;
        userService.save(user);
        userService.save(user2);
        requestService.save(itemRequest);
    }

    @Test
    void testSave() throws ObjectNotFoundException {
        requestService.save(itemRequest2);
        assertEquals(itemRequest2, requestService.get(itemRequest2.getId()));
    }

    @Test
    void testGet() throws ObjectNotFoundException {
        assertEquals(itemRequest, requestService.get(itemRequest.getId()));
    }

    @Test
    void testGetAllByOwnerId() {
        assertEquals(List.of(), requestService.getAllByOwnerId(user.getId()));
    }
}
