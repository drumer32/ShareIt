package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.requests.model.ItemRequest;

import java.util.List;

public interface ItemRequestService {
    List<ItemRequest> getAll(long userId, int from, int size);

    List<ItemRequest> getAllByOwnerId(long userId);

    ItemRequest get(long id) throws ObjectNotFoundException;

    ItemRequest save(ItemRequest itemRequest);

    void delete(long id) throws ObjectNotFoundException;
}
