package ru.practicum.shareit.requests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.repository.ItemRequestRepository;
import ru.practicum.shareit.requests.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional
@SpringBootTest
public class RequestServiceTest {

    ItemRequestRepository repository;
    ItemRequestService requestService;
    UserService userService;

    private User user = new User(1L, "test", "test@gmail.com");

    private ItemRequest itemRequest = new ItemRequest(
            1L,
            "testDescription",
            user,
            new ArrayList<>(),
            LocalDate.now());

    @Autowired
    public RequestServiceTest(ItemRequestRepository repository,
                              ItemRequestService requestService,
                              UserService userService) {
        this.repository = repository;
        this.requestService = requestService;
        this.userService = userService;
        userService.save(user);
        requestService.save(itemRequest);
    }

    @Test
    void getAll() {
        assertEquals(List.of(itemRequest), requestService.getAll(user.getId(), null));
    }

    @Test
    void get() {
        assertEquals(itemRequest, requestService.get(itemRequest.getId()));
    }

    @Test
    void getAllByOwnerId() {
        assertEquals(List.of(itemRequest), requestService.getAllByOwnerId(user.getId()));
    }

    @Test
    void save() {
        ItemRequest itemRequestTest = new ItemRequest(
                2L,
                "testDescription2",
                user,
                new ArrayList<>(),
                LocalDate.now());
        requestService.save(itemRequestTest);
        assertEquals(itemRequestTest, requestService.get(itemRequestTest.getId()));
    }

    @Test
    void delete() {
        requestService.delete(itemRequest.getId());
        assertNull(repository.findById(itemRequest.getId()).orElse(null));
    }
}
