package ru.practicum.shareitgateway.booking;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareitgateway.booking.dto.BookingState;
import ru.practicum.shareitgateway.booking.dto.CreateBookingDto;

@RestController
@AllArgsConstructor
@RequestMapping("/bookings")
@Validated
public class BookingController {

    private final BookingClient client;

    private static final String HEADER_REQUEST = "X-Sharer-User-Id";

    @PostMapping
    public Object create(@RequestHeader(HEADER_REQUEST) long userId,
        @Valid @RequestBody CreateBookingDto dto
    ) {
        return client.create(userId, dto);
    }

    @PatchMapping("/{bookingId}")
    public Object update(@RequestHeader(HEADER_REQUEST) long userId,
                         @PathVariable long bookingId,
                         @RequestParam("approved") Boolean isApproved
    ) {
        return client.update(userId, bookingId, isApproved);
    }

    @GetMapping("/{bookingId}")
    public Object get(@RequestHeader(HEADER_REQUEST) long userId,
        @PathVariable long bookingId
    ) {
        return client.get(userId, bookingId);
    }

    @GetMapping
    public Object getAllByCurrentUser(@RequestHeader(HEADER_REQUEST) long userId,
        @RequestParam(name = "state", defaultValue = "ALL") BookingState state,
        @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
        @RequestParam(name = "size", defaultValue = "10") @Positive int size
    ) {
        return client.getAllByCurrentUser(userId, state, from, size);
    }

    @GetMapping("/owner")
    public Object getAllByOwnedItems(@RequestHeader(HEADER_REQUEST) long userId,
        @RequestParam(name = "state", defaultValue = "ALL") BookingState state,
        @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
        @RequestParam(name = "size", defaultValue = "10") @Positive int size
    ) {
        return client.getAllByOwnedItems(userId, state, from, size);
    }
}
