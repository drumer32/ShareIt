package ru.practicum.shareit.requests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.requests.repository.ItemRequestRepository;
import ru.practicum.shareit.requests.service.ItemRequestService;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.practicum.shareit.ModelsRepForTests.*;

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
    void save() {
        requestService.save(itemRequest2);
        assertEquals(itemRequest2, requestService.get(itemRequest2.getId()));
    }

    @Test
    void get() {
        assertEquals(itemRequest, requestService.get(itemRequest.getId()));
    }

    @Test
    void getAllByOwnerId() {
        assertEquals(List.of(), requestService.getAllByOwnerId(user.getId()));
    }

}
