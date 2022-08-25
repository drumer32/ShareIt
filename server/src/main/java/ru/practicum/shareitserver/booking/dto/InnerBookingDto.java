package ru.practicum.shareitserver.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InnerBookingDto {
    Long id;

    Long bookerId;
}