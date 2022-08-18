package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.exceptions.ObjectNotValidException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private static final String HEADER_REQUEST = "X-Sharer-User-Id";

    private final BookingService bookingService;
    private final ItemService itemService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    List<OwnerItemDto> getAll(@RequestHeader(HEADER_REQUEST) Long userId) {
        return itemService.getAll(userId).stream().map(item -> {
            OwnerItemDto ownerItemDto = modelMapper.map(item, OwnerItemDto.class);
            ownerItemDto.setLastBooking(bookingService.getLastByItemId(item.getId()));
            ownerItemDto.setNextBooking(bookingService.getNextByItemId(item.getId()));
            ownerItemDto.setComments(itemService.getComments(item.getId()));
            return ownerItemDto;
        }).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    OwnerItemDto get(@PathVariable Long id, @RequestHeader(HEADER_REQUEST) Long userId) throws ObjectNotFoundException {
        Item item = itemService.get(id);
        OwnerItemDto ownerItemDto = modelMapper.map(item, OwnerItemDto.class);
        ownerItemDto.setComments(itemService.getComments(id));
        if (item.getOwner().getId().equals(userId)) {
            ownerItemDto.setLastBooking(bookingService.getLastByItemId(id));
            ownerItemDto.setNextBooking(bookingService.getNextByItemId(id));
        }
        return ownerItemDto;
    }

    @PostMapping
    Item create(@Valid @RequestBody CreateItemDto createItemDto,
                @RequestHeader(HEADER_REQUEST) long userId) throws ObjectNotFoundException {
        Item item = modelMapper.map(createItemDto, Item.class);
        item.setOwner(userService.get(userId));
        return itemService.save(item);
    }

    @PatchMapping("{id}")
    Item update(@PathVariable long id,
                @Valid @RequestBody UpdateItemDto updateItemDto,
                @RequestHeader(HEADER_REQUEST) long userId) throws ObjectNotValidException, ObjectNotFoundException {
        Item item = itemService.get(id);
        if (item.getOwner().getId() != userId) throw new ObjectNotValidException();
        modelMapper.map(updateItemDto, item);
        return itemService.save(item);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable long id) throws ObjectNotFoundException {
        itemService.delete(id);
    }

    @GetMapping("/search")
    List<PublicItemDto> search(@RequestParam(defaultValue = "") String text) {
        return itemService.searchBy(text)
                .stream()
                .map(item -> modelMapper.map(item, PublicItemDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("{id}/comment")
    PublicCommentDto addComment(@PathVariable long id,
                                @Valid @RequestBody CreateCommentDto createCommentDto,
                                @RequestHeader(HEADER_REQUEST) long userId) throws ObjectNotFoundException {
        Comment comment = modelMapper.map(createCommentDto, Comment.class);
        comment.setItem(itemService.get(id));
        comment.setAuthor(userService.get(userId));

        itemService.saveComment(comment);

        return modelMapper.map(comment, PublicCommentDto.class);
    }
}