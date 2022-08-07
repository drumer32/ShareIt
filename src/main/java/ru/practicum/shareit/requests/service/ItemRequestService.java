package ru.practicum.shareit.requests.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.model.PublicItemRequestDto;

import java.util.List;

public interface ItemRequestService {
    List<ItemRequest> getAll(long userId, Pageable pageable);

    List<ItemRequest> getAllByOwnerId(long userId);

    ItemRequest get(long id);

    ItemRequest save(ItemRequest itemRequest);

    void delete(long id);
}
