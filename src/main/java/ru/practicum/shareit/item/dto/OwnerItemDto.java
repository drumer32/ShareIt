package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.InnerBookingDto;

import java.util.List;

@Data
@NoArgsConstructor
public class OwnerItemDto {
    Long id;

    String name;

    String description;

    Boolean available;

    List<PublicCommentDto> comments;

    InnerBookingDto lastBooking;

    InnerBookingDto nextBooking;
}
