package ru.practicum.shareitserver.requests.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareitserver.item.dto.PublicItemDto;

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
