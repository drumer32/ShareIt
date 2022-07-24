package ru.practicum.shareit.item.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateItemDto {
    @Size(max = 255) String name;

    @Size(max = 4000) String description;

    Boolean available;
}
