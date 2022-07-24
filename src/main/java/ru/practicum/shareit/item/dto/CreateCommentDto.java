package ru.practicum.shareit.item.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class CreateCommentDto {
    @NotBlank @Size(max = 4000) String text;

    LocalDateTime created = LocalDateTime.now();
}
