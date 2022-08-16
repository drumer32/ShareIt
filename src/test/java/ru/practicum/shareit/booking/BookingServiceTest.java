package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.shareit.ModelsRepForTests.*;

@Transactional
@SpringBootTest
public class BookingServiceTest {

    private final BookingRepository bookingRepository;

    private final BookingService bookingService;

    private User user = new User(
            1L,
            "test",
            "test@gmail.com"
    );

    private User user2 = new User(
            2L,
            "test2",
            "test2@gmail.com"
    );



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
    void testGet() {
        assertEquals(booking, bookingService.get(booking.getId()));
    }

    @Test
    void testDelete() {
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

    @Test
    void testGetLastByItemId() {
        assertEquals(booking, bookingService.getLastByItemId(booking.getItem().getId()));
    }

//    @Test
//    void testGetNextByItemId() {
//        Booking booking1 = bookingRepository.save(booking.setStart(LocalDateTime.plusDays(2)));
//        assertEquals(booking1, bookingService.getNextByItemId(booking1.getItem().getId()));
//    }

    @Test
    void testIsHasNotBookingsByItemIdAndUserId() {
        assertTrue(bookingService.isHasBookingsByItemIdAndUserId(item.getId(), user2.getId()));
    }
}
