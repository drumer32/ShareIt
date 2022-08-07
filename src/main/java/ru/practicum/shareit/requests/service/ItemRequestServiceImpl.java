package ru.practicum.shareit.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.repository.ItemRequestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;

    @Override
    public List<ItemRequest> getAll(long userId, Pageable pageable) {
        return itemRequestRepository.findAllByRequesterIdNot(userId, pageable);
    }

    @Override
    public List<ItemRequest> getAllByOwnerId(long id) {
        return itemRequestRepository.findAllByRequesterId(id);
    }

    @Override
    public ItemRequest get(long id) {
        return itemRequestRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public ItemRequest save(ItemRequest itemRequest) {
        return itemRequestRepository.save(itemRequest);
    }

    @Override
    @Transactional
    public void delete(long id) {
        itemRequestRepository.delete(itemRequestRepository.getReferenceById(id));
    }

}
