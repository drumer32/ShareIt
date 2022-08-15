package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.ObjectNotValidException;
import ru.practicum.shareit.requests.model.ItemRequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.model.PublicItemRequestDto;
import ru.practicum.shareit.requests.service.ItemRequestService;
import ru.practicum.shareit.requests.service.OffsetPageRequest;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private static final String HEADER_REQUEST = "X-Sharer-User-Id";
    private final ItemRequestService itemRequestService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    List<ItemRequest> getAll(@RequestParam(defaultValue = "0") int from,
                                      @RequestParam(defaultValue = "5") int size,
                                      @RequestHeader(HEADER_REQUEST) long userId) {
        return itemRequestService.getAll(userId, OffsetPageRequest.of(from, size));
    }

    @GetMapping("{id}")
    PublicItemRequestDto get(@PathVariable long id) {
        ItemRequest itemRequest = itemRequestService.get(id);
        return modelMapper.map(itemRequest, PublicItemRequestDto.class);
    }

    @GetMapping
    List<ItemRequest> getAllByOwnerId(@RequestHeader(HEADER_REQUEST) long userId) {
        return itemRequestService.getAllByOwnerId(userId);
    }

    @PostMapping
    PublicItemRequestDto create(@Valid @RequestBody ItemRequestDto itemRequestDto) {
        ItemRequest itemRequest = modelMapper.map(itemRequestDto, ItemRequest.class);
        return modelMapper.map(itemRequestService.save(itemRequest), PublicItemRequestDto.class);
    }

    @PatchMapping("{id}")
    PublicItemRequestDto update(@PathVariable long id,
                       @Valid @RequestBody ItemRequestDto itemRequestDto,
                       @RequestHeader(HEADER_REQUEST) long userId) throws ObjectNotValidException {
        ItemRequest itemRequest = itemRequestService.get(id);
        if (itemRequest.getRequester().getId() != userId) throw new ObjectNotValidException();
        modelMapper.map(itemRequestDto, itemRequest);
        return modelMapper.map(itemRequestService.save(itemRequest), PublicItemRequestDto.class);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        itemRequestService.delete(id);
    }
}
