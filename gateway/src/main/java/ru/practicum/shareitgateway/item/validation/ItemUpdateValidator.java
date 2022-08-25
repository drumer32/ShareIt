package ru.practicum.shareitgateway.item.validation;

import ru.practicum.shareitgateway.item.dto.UpdateItemDto;

public class ItemUpdateValidator {

    public boolean isValid(UpdateItemDto item) {
        if (item.getName() == null
            && item.getDescription() == null
            && item.getAvailable() == null) {
            return false;
        }

        if (item.getName() != null && item.getName().isBlank()) {
            return false;
        }

        if (item.getDescription() != null && item.getDescription().isBlank()) {
            return false;
        }

        return true;
    }
}
