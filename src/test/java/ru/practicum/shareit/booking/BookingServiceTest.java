package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.shareit.ModelsRepForTests.*;

@Transactional
@SpringBootTest
public class BookingServiceTest {

    private final BookingRepository bookingRepository;

    private final BookingService bookingService;

    @Autowired
    public BookingServiceTest(BookingRepository bookingRepository,
                              BookingService bookingService,
                              ItemService itemService,
                              UserService userService) {
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
        userService.save(user);
        userService.save(user2);
        itemService.save(item);
        bookingRepository.save(booking);
    }

    @Test
    void testCreate() {
        Booking booking1 = bookingService.save(booking);
        assertEquals(bookingRepository.findById(booking.getId()).orElse(null), booking1);
    }

    @Test
    void testGet() throws ObjectNotFoundException {
        assertEquals(booking, bookingService.get(booking.getId()));
    }

    @Test
    void testDelete() throws ObjectNotFoundException {
        bookingService.delete(booking.getId());
        assertNull(bookingRepository.findById(booking.getId()).orElse(null));
    }

    @Test
    void testGetAllByCurrentUserALL() {
        assertEquals(List.of(booking), bookingService.getAllByCurrentUser(user2.getId(), "ALL"));
    }

    @Test
    void testGetAllByOwnedItemsALL() {
        assertEquals(List.of(booking), bookingService.getAllByOwnedItems(user.getId(), "ALL"));
    }
}
