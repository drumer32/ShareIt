package ru.practicum.shareit.item;


import  org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.practicum.shareit.booking.model.Status.APPROVED;

@Transactional
@SpringBootTest
public class ItemServiceTest {

    private ItemService itemService;
    private UserService userService;
    private BookingService bookingService;
    ItemRepository itemRepository;
    UserRepository userRepository;
    CommentRepository commentRepository;

    private User user = new User(
            1L,
            "test",
            "test@gmail.com");

    private User user2 = new User(
            2L,
            "test2",
            "test2@gmail.com");

    private Item item = new Item(
            1L,
            "name",
            "description",
            true,
            user,
            new ArrayList<>(),
            1L
    );

    private Booking booking = new Booking(
            1L,
            LocalDateTime.now(),
            LocalDateTime.now(),
            item,
            user2,
            APPROVED
    );

    @Autowired
    public ItemServiceTest(CommentRepository commentRepository,
                           UserRepository userRepository,
                           ItemRepository itemRepository,
                           BookingService bookingService,
                           ItemService itemService) {
        userRepository.save(user);
        userRepository.save(user2);
        this.itemRepository = itemRepository;
        itemRepository.save(item);
        this.bookingService = bookingService;
        this.itemService = itemService;
    }

    @Test
    void getAll() {
        assertEquals(List.of(item), itemService.getAll(user.getId()));
    }

    @Test
    void get() {
        assertEquals(item, itemService.get(item.getId()));
    }

    @Test
    void save() {
        Item item2 = new Item(
                1L,
                "name2",
                "description2",
                true,
                user,
                new ArrayList<>(),
                1L
        );
        itemRepository.save(item2);
        assertEquals(itemRepository.findById(user.getId()).orElse(null), item2);
    }

    @Test
    void deleteItem() {
        itemService.delete(item.getId());
        assertNull(itemRepository.findById(user.getId()).orElse(null));
    }

    @Test
    void searchBy() {
        assertEquals(List.of(item), itemService.searchBy("na"));
    }

    @Test
    void saveComment() {
        Comment comment = new Comment(
                1L,
                "comment",
                item,
                user,
                LocalDateTime.now()
        );
        assertEquals(commentRepository.findById(comment.getId()).orElse(null), comment);
    }
}
