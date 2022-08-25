package ru.practicum.shareitserver.booking.service;

import ru.practicum.shareitserver.booking.model.Booking;
import ru.practicum.shareitserver.booking.dto.InnerBookingDto;
import ru.practicum.shareitserver.exceptions.ObjectNotFoundException;

import java.util.List;

public interface BookingService {

    Booking get(Long id) throws ObjectNotFoundException;

    Booking save(Booking booking);

    void delete(long id) throws ObjectNotFoundException;

    List<Booking> getAllByCurrentUser(long userId, String state);

    List<Booking> getAllByOwnedItems(long userId, String state);

    InnerBookingDto getLastByItemId(long itemId);

    InnerBookingDto getNextByItemId(long itemId);

}
