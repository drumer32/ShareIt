package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.InnerBookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.dto.InnerBookingDto;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    private final InnerBookingMapper modelMapper;

    @Override
    public Booking get(Long id) throws ObjectNotFoundException {
        log.info("Запрос на получение бронирования - {}", id);
        return bookingRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    @Transactional
    public Booking save(Booking booking) {
        log.info("Запрос на сохранение бронирования - {}", booking.getId());
        return bookingRepository.save(booking);
    }

    @Override
    public void delete(long id) throws ObjectNotFoundException {
        log.info("Запрос на удаление бронирования - {}", id);
        bookingRepository.delete(get(id));
    }

    @Override
    public List<Booking> getAllByCurrentUser(long userId, String state) {
        log.info("Запрос на получение бронирований пользователя - {}", userId);
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
        log.info("Запрос на получение бронирований пользователя - {}", userId);
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
        log.info("Запрос на получение последнего бронирования у вещи - {}", itemId);
        Booking booking = bookingRepository.getFirstByItemIdOrderByStartAsc(itemId);
        if (booking == null) return null;
        InnerBookingDto innerBookingDto = modelMapper.convert(booking);
        innerBookingDto.setBookerId(booking.getBooker().getId());
        return innerBookingDto;
    }

    @Override
    public InnerBookingDto getNextByItemId(long itemId) {
        log.info("Запрос на получение первого бронирования у вещи - {}", itemId);
        Booking booking = bookingRepository.getFirstByItemIdOrderByEndDesc(itemId);
        if (booking == null) return null;
        InnerBookingDto innerBookingDto = modelMapper.convert(booking);
        innerBookingDto.setBookerId(booking.getBooker().getId());
        return innerBookingDto;
    }
}
