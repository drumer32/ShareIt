package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exceptions.ItemNotAvailableException;
import ru.practicum.shareit.exceptions.ObjectNotValidException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private static final String HEADER_REQUEST = "X-Sharer-User-Id";
    private final BookingService bookingService;
    private final ItemService itemService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("{id}")
    Booking get(@PathVariable long id, @RequestHeader(HEADER_REQUEST) long userId) throws ObjectNotValidException {
        Booking booking = bookingService.get(id);

        if (!(booking.getBooker().getId() == userId || booking.getItem().getOwner().getId() == userId)) {
            throw new ObjectNotValidException();
        }

        return booking;
    }

    @PostMapping
    Booking create(@Valid @RequestBody BookingDto createBookingDto,
                   @RequestHeader(HEADER_REQUEST) Long userId) {
        Item item = itemService.get(createBookingDto.getItemId());
        User user = userService.get(userId);

        if (createBookingDto.getEnd().isBefore(createBookingDto.getStart())) {
            throw new ValidationException();
        }

        Booking booking = modelMapper.map(createBookingDto, Booking.class);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStatus(Status.WAITING);

        return bookingService.save(booking);
    }

    @PatchMapping("{id}")
    Booking update(@PathVariable long id, @RequestParam boolean approved, @RequestHeader(HEADER_REQUEST) long userId) throws ObjectNotValidException, ItemNotAvailableException {
        Booking booking = bookingService.get(id);

        if (booking.getItem().getOwner().getId() != userId) throw new ObjectNotValidException();
        if (booking.getStatus().equals(Status.APPROVED)) {
            throw new ItemNotAvailableException();
        }

        booking.setStatus(approved ? Status.APPROVED : Status.REJECTED);

        return bookingService.save(booking);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        bookingService.delete(id);
    }

    @GetMapping
    List<Booking> getAllByCurrentUser(@RequestParam(defaultValue = "ALL") String state,
                                      @RequestHeader(HEADER_REQUEST) long userId) {
        return bookingService.getAllByCurrentUser(userId, state);
    }

    @GetMapping("/owner")
    List<Booking> getAllByOwnedItems(@RequestParam(defaultValue = "ALL") String state,
                                     @RequestHeader(HEADER_REQUEST) long userId) {
        return bookingService.getAllByOwnedItems(userId, state);
    }
}
