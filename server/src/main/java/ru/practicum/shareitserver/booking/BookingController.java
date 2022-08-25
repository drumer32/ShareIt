package ru.practicum.shareitserver.booking;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareitserver.booking.dto.BookingDto;
import ru.practicum.shareitserver.booking.model.Booking;
import ru.practicum.shareitserver.booking.model.Status;
import ru.practicum.shareitserver.booking.service.BookingService;
import ru.practicum.shareitserver.exceptions.ItemNotAvailableException;
import ru.practicum.shareitserver.exceptions.ObjectNotFoundException;
import ru.practicum.shareitserver.exceptions.ObjectNotValidException;
import ru.practicum.shareitserver.item.model.Item;
import ru.practicum.shareitserver.item.repository.ItemRepository;
import ru.practicum.shareitserver.user.service.UserService;

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
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("{id}")
    Booking get(@PathVariable long id, @RequestHeader(HEADER_REQUEST) long userId) throws ObjectNotValidException, ObjectNotFoundException {
        Booking booking = bookingService.get(id);

        if (!(booking.getBooker().getId() == userId || booking.getItem().getOwner().getId() == userId)) {
            throw new ObjectNotValidException();
        }

        return booking;
    }

    @PostMapping
    Booking create(@Valid @RequestBody BookingDto createBookingDto,
                   @RequestHeader(HEADER_REQUEST) Long userId) throws ObjectNotFoundException {

        if (createBookingDto.getEnd().isBefore(createBookingDto.getStart())) {
            throw new ValidationException();
        }

        Item item = itemRepository.getReferenceById(createBookingDto.getItemId());
        Booking booking = modelMapper.map(createBookingDto, Booking.class);
        booking.setItem(item);
        booking.setBooker(userService.get(userId));
        booking.setStatus(Status.WAITING);
        return bookingService.save(booking);
    }

    @PatchMapping("{id}")
    Booking update(@PathVariable long id, @RequestParam boolean approved, @RequestHeader(HEADER_REQUEST) long userId) throws ObjectNotValidException, ItemNotAvailableException, ObjectNotFoundException {
        Booking booking = bookingService.get(id);

        if (booking.getItem().getOwner().getId() != userId) throw new ObjectNotValidException();
        if (booking.getStatus().equals(Status.APPROVED)) {
            throw new ItemNotAvailableException();
        }

        booking.setStatus(approved ? Status.APPROVED : Status.REJECTED);

        return bookingService.save(booking);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable long id) throws ObjectNotFoundException {
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
