package ru.practicum.shareitserver.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareitserver.exceptions.ObjectNotFoundException;
import ru.practicum.shareitserver.item.dto.PublicCommentDto;
import ru.practicum.shareitserver.item.model.Comment;
import ru.practicum.shareitserver.item.model.Item;
import ru.practicum.shareitserver.item.repository.CommentRepository;
import ru.practicum.shareitserver.item.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final CommentRepository commentRepository;

    private final ItemRepository itemRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<Item> getAll(Long userId) {
        log.info("Запрос на получение всех вещей");
        return itemRepository.getAllByOwnerId(userId);
    }

    @Override
    public Item get(Long id) throws ObjectNotFoundException {
        log.info("Запрос на получение вещи с id - {}", id);
        return itemRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    @Transactional
    public Item save(Item item) {
        log.info("Запрос на сохранение вещи с id - {}", item.getId());
        return itemRepository.save(item);
    }

    @Override
    @Transactional
    public void delete(Long id) throws ObjectNotFoundException {
        log.info("Запрос на удаление вещи с id - {}", id);
        itemRepository.delete(get(id));
    }

    @Override
    public List<Item> searchBy(String text) {
        if (text.equals("")) return new ArrayList<>();
        log.info("Запрос на поиск вещи по - {}", text);
        return itemRepository.getAllByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCaseAndAvailableIsTrue(text, text);
    }

    @Override
    public Comment saveComment(Comment comment) {
        log.info("Запрос на сохранение комментария - {}", comment);
        return commentRepository.save(comment);
    }

    @Override
    public List<PublicCommentDto> getComments(Long itemId) {
        log.info("Запрос на получение комментариев вещи - {}", itemId);
        return commentRepository.getAllByItemId(itemId).stream().map(comment -> {
            PublicCommentDto publicCommentDto = modelMapper.map(comment, PublicCommentDto.class);
            publicCommentDto.setAuthorName(comment.getAuthor().getName());
            return publicCommentDto;
        }).collect(Collectors.toList());
    }
}