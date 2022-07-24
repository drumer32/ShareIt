package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.requests.model.ItemRequest;

import java.util.List;

public interface ItemRequestService {
    List<ItemRequest> getAll();

    ItemRequest get(long id);

    ItemRequest save(ItemRequest itemRequest);

    void delete(long id);
}
