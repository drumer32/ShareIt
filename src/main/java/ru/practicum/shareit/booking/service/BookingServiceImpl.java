package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.InnerBookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.dto.InnerBookingDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    private final InnerBookingMapper modelMapper;

    @Override
    public Booking get(Long id) {
        return bookingRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void delete(long id) {
        bookingRepository.delete(bookingRepository.getReferenceById(id));
    }

    @Override
    public List<Booking> getAllByCurrentUser(long userId, String state) {
        LocalDateTime now = LocalDateTime.now();
        switch (state) {
            case "WAITING":
                return bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, Status.WAITING);
            case "REJECTED":
                return bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
            case "PAST":
                return bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(userId, now);
            case "FUTURE":
                return bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(userId, now);
            case "CURRENT":
                return bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(userId, now, now);
            default:
                return bookingRepository.findAllByBookerIdOrderByStartDesc(userId);
        }
    }

    @Override
    public List<Booking> getAllByOwnedItems(long userId, String state) {
        LocalDateTime now = LocalDateTime.now();
        switch (state) {
            case "WAITING":
                return bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.WAITING);
            case "REJECTED":
                return bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
            case "PAST":
                return bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(userId, now);
            case "FUTURE":
                return bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(userId, now);
            case "CURRENT":
                return bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(userId, now, now);
            default:
                return bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId);
        }
    }

    @Override
    public InnerBookingDto getLastByItemId(long itemId) {
        Booking booking = bookingRepository.getFirstByItemIdOrderByStartAsc(itemId);
        if (booking == null) return null;
        InnerBookingDto innerBookingDto = modelMapper.convert(booking);
        innerBookingDto.setBookerId(booking.getBooker().getId());
        return innerBookingDto;
    }

    @Override
    public InnerBookingDto getNextByItemId(long itemId) {
        Booking booking = bookingRepository.getFirstByItemIdOrderByEndDesc(itemId);
        if (booking == null) return null;
        InnerBookingDto innerBookingDto = modelMapper.convert(booking);
        innerBookingDto.setBookerId(booking.getBooker().getId());
        return innerBookingDto;
    }

    @Override
    public boolean isHasBookingsByItemIdAndUserId(long itemId, long userId) {
        return bookingRepository.countByItemIdAndBookerIdAndStatusAndStartBefore(itemId, userId,
                Status.APPROVED, LocalDateTime.now()) > 0;
    }
}
