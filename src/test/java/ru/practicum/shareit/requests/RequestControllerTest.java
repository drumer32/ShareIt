package ru.practicum.shareit.requests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.ShareItTests.objectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ItemRequestService itemRequestService;

    private static final String HEADER_REQUEST = "X-Sharer-User-Id";
    private User user = new User(
            1L, 
            "test", 
            "test@gmail.com");

    private ItemRequest itemRequest = new ItemRequest(
            1L,
            "testDescription",
            user,
            new ArrayList<>(),
            LocalDate.now());

    @Test
    void testGetAll() throws Exception {
        when(itemRequestService.getAll(anyLong(), any()))
                .thenReturn(List.of(itemRequest));
        mvc.perform(get("/requests/all")
                        .header(HEADER_REQUEST, user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is(itemRequest.getDescription())));
    }


    @Test
    void testGet() throws Exception {
        when(itemRequestService.get(anyLong()))
                .thenReturn(itemRequest);
        mvc.perform(get("/requests/{id}", itemRequest.getId())
                        .header(HEADER_REQUEST, user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(itemRequest.getDescription())));
    }

    @Test
    void testGetAllByOwnerId() throws Exception {
        when(itemRequestService.getAllByOwnerId(anyLong()))
                .thenReturn(List.of(itemRequest));
        mvc.perform(get("/requests")
                        .header(HEADER_REQUEST, user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is(itemRequest.getDescription())));
    }

    @Test
    void testCreate() throws Exception {
        when(itemRequestService.save(any()))
                .thenReturn(itemRequest);
        mvc.perform(post("/requests")
                        .content(objectMapper.writeValueAsString(itemRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(itemRequest.getDescription())));
    }

    @Test
    void testUpdate() throws Exception {
        ItemRequest itemRequest1 = new ItemRequest(1L, "testDescriptionUpdated",
                user, new ArrayList<>(), LocalDate.now());
        when(itemRequestService.get(anyLong()))
                .thenReturn(itemRequest);
        when(itemRequestService.save(any()))
                .thenReturn(itemRequest1);
        mvc.perform(patch("/requests/{id}", itemRequest.getId())
                        .header(HEADER_REQUEST, user.getId())
                .content(objectMapper.writeValueAsString(itemRequest1))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(itemRequest1.getDescription())));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(itemRequestService)
                .delete(itemRequest.getId());
        mvc.perform(delete("/requests/{id}", itemRequest.getId()))
                .andExpect(status().isOk());
        verify(itemRequestService, times(1)).delete(itemRequest.getId());
    }
}