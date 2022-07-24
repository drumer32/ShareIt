package ru.practicum.shareit.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.repository.ItemRequestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;

    @Override
    public List<ItemRequest> getAll() {
        return itemRequestRepository.findAll();
    }

    @Override
    public ItemRequest get(long id) {
        return itemRequestRepository.findById(id).orElseThrow();
    }

    @Override
    public ItemRequest save(ItemRequest itemRequest) {
        return itemRequestRepository.save(itemRequest);
    }

    @Override
    public void delete(long id) {
        itemRequestRepository.delete(itemRequestRepository.getReferenceById(id));
    }
}
