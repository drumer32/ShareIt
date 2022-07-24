package ru.practicum.shareit.item.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateItemDto {
    @NotBlank @Size(max = 255) String name;

    @NotBlank @Size(max = 4000) String description;

    @NotNull Boolean available;
}
