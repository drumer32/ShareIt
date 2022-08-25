package ru.practicum.shareitgateway.item;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareitgateway.exceptions.ObjectNotValidException;
import ru.practicum.shareitgateway.item.dto.CreateCommentDto;
import ru.practicum.shareitgateway.item.dto.CreateItemDto;
import ru.practicum.shareitgateway.item.dto.UpdateItemDto;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
@Validated
public class ItemController {

    private final ItemClient client;

    private static final String HEADER_REQUEST = "X-Sharer-User-Id";

    @PostMapping
    public Object create(@RequestHeader(HEADER_REQUEST) long userId,
        @Valid @RequestBody CreateItemDto dto
    ) {
        return client.create(userId, dto);
    }

    @PatchMapping("/{id}")
    public Object update(@RequestHeader(HEADER_REQUEST) long userId,
        @PathVariable("id") long itemId,
        @Valid @RequestBody UpdateItemDto dto
    ) throws ObjectNotValidException {
        return client.update(userId, itemId, dto);
    }

    @GetMapping("/{id}")
    public Object get(@RequestHeader(HEADER_REQUEST) long userId,
        @PathVariable("id") long itemId
    ) {
        return client.get(userId, itemId);
    }

    @GetMapping
    public Object getAllOfUser(@RequestHeader(HEADER_REQUEST) long userId,
        @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
        @RequestParam(name = "size", defaultValue = "10") @Positive int size
    ) {
        return client.getAllOfUser(userId, from, size);
    }

    @GetMapping("/search")
    public Object search(
        @RequestParam("text") String searchText,
        @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
        @RequestParam(name = "size", defaultValue = "10") @Positive int size
    ) {
        return client.search(searchText, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public Object addComment(@RequestHeader(HEADER_REQUEST) long userId,
        @PathVariable long itemId,
        @RequestBody @Valid CreateCommentDto dto
    ) {
        return client.addComment(userId, itemId, dto);
    }
}
