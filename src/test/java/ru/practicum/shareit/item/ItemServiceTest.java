package ru.practicum.shareit.item;

import  org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static ru.practicum.shareit.ModelsRepForTests.*;

@Transactional
@SpringBootTest
public class ItemServiceTest {

    ItemService itemService;
    UserRepository userRepository;
    BookingService bookingService;
    ItemRepository itemRepository;
    CommentRepository commentRepository;

    @Autowired
    public ItemServiceTest(UserRepository userRepository,
                           ItemRepository itemRepository,
                           BookingService bookingService,
                           ItemService itemService) {
        this.userRepository = userRepository;
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
        assertEquals(commentRepository.findById(comment.getId()).orElse(null), comment);
    }
}
