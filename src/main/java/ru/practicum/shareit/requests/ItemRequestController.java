package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.ObjectNotValidException;
import ru.practicum.shareit.requests.model.ItemRequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.service.ItemRequestService;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private static final String HEADER_REQUEST = "X-Sharer-User-Id";
    private final ItemRequestService itemRequestService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    List<ItemRequest> getAll() {
        return itemRequestService.getAll();
    }

    @GetMapping("{id}")
    ItemRequest get(@PathVariable long id) {
        return itemRequestService.get(id);
    }

    @PostMapping
    ItemRequest create(@Valid @RequestBody ItemRequestDto itemRequestDto,
                       @RequestHeader(HEADER_REQUEST) long userId) {
        ItemRequest itemRequest = modelMapper.map(itemRequestDto, ItemRequest.class);
        itemRequest.setRequester(userService.get(userId));
        return itemRequestService.save(itemRequest);
    }

    @PatchMapping("{id}")
    ItemRequest update(@PathVariable long id,
                       @Valid @RequestBody ItemRequestDto itemRequestDto,
                       @RequestHeader(HEADER_REQUEST) long userId) throws ObjectNotValidException {
        ItemRequest itemRequest = itemRequestService.get(id);
        if (itemRequest.getRequester().getId() != userId) throw new ObjectNotValidException();
        modelMapper.map(itemRequestDto, itemRequest);
        return itemRequestService.save(itemRequest);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        itemRequestService.delete(id);
    }
}
