package ru.practicum.shareit;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static ru.practicum.shareit.booking.model.Status.WAITING;

public class ModelsRepForTests {

    public static User user = new User(
            1L,
            "test",
            "test@gmail.com");

    public static User user2 = new User(
            2L,
            "test2",
            "test2@gmail.com");

    public static User user3 = new User(
            3L,
            "test3",
            "test3@gmail.com");

    public static ItemRequest itemRequest = new ItemRequest(
            1L,
            "testDescription",
            user2,
            LocalDate.now()
    );

    public static ItemRequest itemRequest2 = new ItemRequest(
            2L,
            "testDescription2",
            null,
            LocalDate.now()
    );

    public static Item item = new Item(
            1L,
            "name",
            "description",
            true,
            user,
            new ArrayList<>(),
            itemRequest
    );

    public static Item item2 = new Item(
            2L,
            "name2",
            "description2",
            true,
            user2,
            new ArrayList<>(),
            itemRequest
    );

    public static Comment comment = new Comment(
            1L,
            "comment",
            item,
            user,
            LocalDateTime.now()
    );
    public static Booking booking = new Booking(
            1L,
            LocalDateTime.of(2022, 1, 1, 12, 20),
            LocalDateTime.of(2022, 2, 1, 12, 20),
            item,
            user2,
            WAITING
    );
}
