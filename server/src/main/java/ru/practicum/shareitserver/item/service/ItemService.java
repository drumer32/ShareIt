package ru.practicum.shareitserver.item.service;

import ru.practicum.shareitserver.exceptions.ObjectNotFoundException;
import ru.practicum.shareitserver.item.dto.PublicCommentDto;
import ru.practicum.shareitserver.item.model.Comment;
import ru.practicum.shareitserver.item.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAll(Long userId);

    Item get(Long id) throws ObjectNotFoundException;

    Item save(Item item);

    void delete(Long id) throws ObjectNotFoundException;

    List<Item> searchBy(String text);

    Comment saveComment(Comment comment);

    List<PublicCommentDto> getComments(Long itemId);
}
