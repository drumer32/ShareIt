package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.dto.InnerBookingDto;

import java.util.List;

public interface BookingService {

    Booking get(Long id);

    Booking save(Booking booking);

    void delete(long id);

    List<Booking> getAllByCurrentUser(long userId, String state);

    List<Booking> getAllByOwnedItems(long userId, String state);

    InnerBookingDto getLastByItemId(long itemId);

    InnerBookingDto getNextByItemId(long itemId);

}
