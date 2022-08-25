package ru.practicum.shareitgateway.item.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UpdateItemDto {

    private String name;

    private String description;

    private Boolean available;
}
