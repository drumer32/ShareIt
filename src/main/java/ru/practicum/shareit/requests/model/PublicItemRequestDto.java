package ru.practicum.shareit.requests.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.PublicItemDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class PublicItemRequestDto {

    Long id;

    String description;

    LocalDateTime created;

    List<PublicItemDto> items;
}
