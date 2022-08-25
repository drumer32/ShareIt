package ru.practicum.shareitserver.requests.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareitserver.exceptions.ObjectNotFoundException;
import ru.practicum.shareitserver.requests.model.ItemRequest;
import ru.practicum.shareitserver.requests.repository.ItemRequestRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;

    @Override
    public List<ItemRequest> getAll(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from, size, Sort.by("created").descending());
        Page<ItemRequest> requests = itemRequestRepository.findAll(pageable);
        log.info("Запрос на получение всех заявок");
        return new ArrayList<>(requests.getContent());
    }

    @Override
    public List<ItemRequest> getAllByOwnerId(long id) {
        log.info("Запрос на получение всех заявок пользователя id - {}", id);
        return itemRequestRepository.findAllByRequesterId(id);
    }

    @Override
    public ItemRequest get(long id) throws ObjectNotFoundException {
        log.info("Запрос на получение заявки с id - {}", id);
        return itemRequestRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    @Transactional
    public ItemRequest save(ItemRequest itemRequest) {
        log.info("Запрос на сохранение заявки с id - {}", itemRequest.getId());
        return itemRequestRepository.save(itemRequest);
    }

    @Override
    @Transactional
    public void delete(long id) throws ObjectNotFoundException {
        log.info("Запрос на удаление заявки с id - {}", id);
        itemRequestRepository.delete(get(id));
    }

}
