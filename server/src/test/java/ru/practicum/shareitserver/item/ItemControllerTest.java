package ru.practicum.shareitserver.item;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import  org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareitserver.item.model.Item;
import ru.practicum.shareitserver.item.service.ItemService;
import ru.practicum.shareitserver.user.service.UserService;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareitserver.ShareItTests.objectMapper;
import static ru.practicum.shareitserver.ModelsRepForTests.*;

@AutoConfigureMockMvc
@SpringBootTest
public class ItemControllerTest {

    static final String HEADER_REQUEST = "X-Sharer-User-Id";

    @Autowired
    MockMvc mvc;

    @MockBean
    ItemService itemService;

    @MockBean
    UserService userService;

    @Test
    public void testGetAll() throws Exception {
        when(itemService.getAll(anyLong()))
                .thenReturn(List.of(item));
        mvc.perform(get("/items")
                        .header(HEADER_REQUEST, user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(item.getName())))
                .andExpect(jsonPath("$[0].description", is(item.getDescription())));
    }

    @Test
    public void testCreate() throws Exception {
        userService.save(user);
        when(itemService.save(any()))
                .thenReturn(item);
        mvc.perform(post("/items")
                        .content(objectMapper.writeValueAsString(item))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HEADER_REQUEST, user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(item.getDescription())));
    }

    @Test
    public void testUpdate() throws Exception {
        Item itemNew = new Item(
                1L,
                "name",
                "descriptionUpdated",
                true,
                user,
                new ArrayList<>(),
                itemRequest
        );
        when(itemService.get(anyLong()))
                .thenReturn(item);
        when(itemService.save(any()))
                .thenReturn(itemNew);
        mvc.perform(patch("/items/{id}", item.getId())
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
        mvc.perform(get("/items/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is(item.getDescription())));
    }

    @Test
    public void addCommentTest() throws Exception {
        userService.save(user);
        when(itemService.saveComment(any()))
                .thenReturn(comment);
        mvc.perform(post("/items/{id}/comment", item.getId())
                        .content(objectMapper.writeValueAsString(comment))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HEADER_REQUEST, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(comment.getText())));
    }
}
