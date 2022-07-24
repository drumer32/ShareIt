package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.PublicCommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {
    private final CommentRepository commentRepository;

    private final ItemRepository itemRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<Item> getAll(long userId) {
        return itemRepository.getAllByOwnerId(userId);
    }

    @Override
    public Item get(long id) {
        return itemRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    @Transactional
    public void delete(long id) {
        itemRepository.delete(itemRepository.getReferenceById(id));
    }

    @Override
    public List<Item> searchBy(String text) {
        if (text.equals("")) return new ArrayList<>();
        return itemRepository.getAllByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCaseAndAvailableIsTrue(text, text);
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<PublicCommentDto> getComments(long itemId) {
        return commentRepository.getAllByItemId(itemId).stream().map(comment -> {
            PublicCommentDto publicCommentDto = modelMapper.map(comment, PublicCommentDto.class);
            publicCommentDto.setAuthorName(comment.getAuthor().getName());
            return publicCommentDto;
        }).collect(Collectors.toList());
    }
}