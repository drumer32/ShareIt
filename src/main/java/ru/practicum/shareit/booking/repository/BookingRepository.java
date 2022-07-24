package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(long userId, Status status);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime before);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(long userId, LocalDateTime after);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(long userId,
                                                                             LocalDateTime before,
                                                                             LocalDateTime after);

    List<Booking> findAllByBookerIdOrderByStartDesc(long userId);

    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(long userId, Status status);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime before);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(long userId, LocalDateTime after);

    List<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(long userId,
                                                                                LocalDateTime before,
                                                                                LocalDateTime after);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(long userId);

    Booking getFirstByItemIdOrderByEndDesc(long itemId);

    Booking getFirstByItemIdOrderByStartAsc(long itemId);

    Integer countByItemIdAndBookerIdAndStatusAndStartBefore(long itemId,
                                                            long userId,
                                                            Status status,
                                                            LocalDateTime now);
}
