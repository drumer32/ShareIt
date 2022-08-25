package ru.practicum.shareitserver.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareitserver.booking.dto.InnerBookingDto;

import java.util.List;

@Data
@NoArgsConstructor
public class OwnerItemDto {
    Long id;

    String name;

    String description;

    Boolean available;

    List<PublicCommentDto> comments;

    Long requestId;

    InnerBookingDto lastBooking;

    InnerBookingDto nextBooking;
}
