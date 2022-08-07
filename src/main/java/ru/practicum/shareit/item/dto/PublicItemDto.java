package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PublicItemDto {
    Long id;

    String name;

    String description;

    Boolean available;

    Long requestId;

    List<PublicCommentDto> comments;
}
