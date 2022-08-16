package ru.practicum.shareit.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.repository.ItemRequestRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;

    @Override
    public List<ItemRequest> getAll(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from, size, Sort.by("created").descending());
        Page<ItemRequest> requests = itemRequestRepository.findAll(pageable);
        return new ArrayList<>(requests.getContent());
    }

    @Override
    public List<ItemRequest> getAllByOwnerId(long id) {
        return itemRequestRepository.findAllByRequesterId(id);
    }

    @Override
    public ItemRequest get(long id) {
        return itemRequestRepository.findById(id).orElse(null);
    }

    @Override
    public ItemRequest save(ItemRequest itemRequest) {
        return itemRequestRepository.save(itemRequest);
    }

    @Override
    public void delete(long id) {
        itemRequestRepository.delete(get(id));
    }

}
