package ru.practicum.shareit.item;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import  org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.ShareItTests.objectMapper;
import static ru.practicum.shareit.ModelsRepForTests.*;

@AutoConfigureMockMvc
@SpringBootTest
public class ItemControllerTest {

    static final String HEADER_REQUEST = "X-Sharer-User-Id";

    @Autowired
    MockMvc mvc;

    @MockBean
    ItemService itemService;

    @Test
    public void getAllTest() throws Exception {
        when(itemService.getAll(anyLong()))
                .thenReturn(List.of(item));
        mvc.perform(get("/items")
                        .header(HEADER_REQUEST, user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(item.getName())))
                .andExpect(jsonPath("$[0].description", is(item.getDescription())));
    }

    @Test
    public void createTest() throws Exception {
        when(itemService.save(any()))
                .thenReturn(item);
        mvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item))
                        .header(HEADER_REQUEST, user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(item.getDescription())));
    }

    @Test
    public void updateTest() throws Exception {
        Item itemNew = new Item(
                1L,
                "name",
                "description2",
                true,
                user,
                new ArrayList<>(),
                1L
        );
        when(itemService.get(anyLong()))
                .thenReturn(item);
        when(itemService.save(any()))
                .thenReturn(itemNew);
        mvc.perform(patch("/items/{id}")
                .header(HEADER_REQUEST, user.getId())
                .content(objectMapper.writeValueAsString(itemNew))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(itemNew.getDescription())));

    }

    @Test
    public void deleteTest() throws Exception {
        doNothing().when(itemService)
                .delete(item.getId());
        mvc.perform(delete("/items/{id}", item.getId()))
                .andExpect(status().isOk());
        verify(itemService, times(1)).delete(item.getId());
    }

    @Test
    public void searchTest() throws Exception {
        when(itemService.searchBy(anyString()))
                .thenReturn(List.of(item));
        mvc.perform(get("items/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(item.getDescription())));
    }

    @Test
    public void addCommentTest() throws Exception {
        when(itemService.saveComment(any()))
                .thenReturn(comment);
        mvc.perform(post("items/{id}/comment")
                .header(HEADER_REQUEST, user.getId())
                .content(objectMapper.writeValueAsString(comment))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(comment.getText())));
    }
}
