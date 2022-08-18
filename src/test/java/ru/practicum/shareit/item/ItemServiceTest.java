package ru.practicum.shareit.item;

import  org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.requests.service.ItemRequestService;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.shareit.ModelsRepForTests.*;

@Transactional
@SpringBootTest
public class ItemServiceTest {

    ItemService itemService;
    UserRepository userRepository;
    BookingService bookingService;
    ItemRepository itemRepository;
    CommentRepository commentRepository;
    ItemRequestService itemRequestService;

    @Autowired
    public ItemServiceTest(UserRepository userRepository,
                           ItemRepository itemRepository,
                           BookingService bookingService,
                           ItemRequestService itemRequestService,
                           ItemService itemService,
                           CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.bookingService = bookingService;
        this.itemRequestService = itemRequestService;
        this.itemService = itemService;
        this.commentRepository = commentRepository;
        userRepository.save(user);
        userRepository.save(user2);
        itemRepository.save(item);
    }

    @Test
    void testGetAll() {
        assertEquals(List.of(item), itemService.getAll(user.getId()));
    }

    @Test
    void testGet() throws ObjectNotFoundException {
        assertEquals(item, itemService.get(item.getId()));
    }

    @Test
    void testSave() throws ObjectNotFoundException {
        itemRepository.save(item2);
        assertEquals(item2, itemService.get(item2.getId()));
    }

    @Test
    void testDeleteItem() throws ObjectNotFoundException {
        itemService.delete(item.getId());
        assertThrows(ObjectNotFoundException.class, () -> itemService.get(item.getId()));
    }

    @Test
    void searchBy() {
        assertEquals(List.of(item), itemService.searchBy("na"));
    }

    @Test
    void saveComment() {
        commentRepository.save(comment);
        assertEquals(comment, commentRepository.findById(comment.getId()).orElse(null));
    }
}
