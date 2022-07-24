package ru.practicum.shareit.requests.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ItemRequestDto {
    @NotBlank String description;
}



