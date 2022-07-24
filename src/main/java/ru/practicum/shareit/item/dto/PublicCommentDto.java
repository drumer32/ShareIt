package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PublicCommentDto {
    Long id;

    String text;

    String authorName;

    LocalDateTime created;
}
