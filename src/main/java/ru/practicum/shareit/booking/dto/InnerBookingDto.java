package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InnerBookingDto {
    Long id;

    Long bookerId;
}