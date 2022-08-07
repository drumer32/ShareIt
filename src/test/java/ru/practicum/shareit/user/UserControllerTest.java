package ru.practicum.shareit.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.ShareItTests.objectMapper;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserService userService;
    private User user3 = new User(3L, "test3", "test3@gmail.com");
    @Autowired
    private MockMvc mvc;

    @Test
    void testGet() throws Exception {
        when(userService.get(user3.getId()))
                .thenReturn(user3);
        mvc.perform(get("/users/{id}", user3.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(user3.getName())))
                .andExpect(jsonPath("$.email", is(user3.getEmail())));
    }

    @Test
    void testGetAll() throws Exception {
        when(userService.getAll())
                .thenReturn(List.of(user3));
        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(notNullValue())))
                .andExpect(jsonPath("$[0].name", is(user3.getName())))
                .andExpect(jsonPath("$[0].email", is(user3.getEmail())));
    }

    @Test
    void testGetAllEmpty() throws Exception {
        when(userService.getAll())
                .thenReturn(List.of());
        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testCreate() throws Exception {
        when(userService.save(any()))
                .thenReturn(user3);
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(user3.getName())))
                .andExpect(jsonPath("$.email", is(user3.getEmail())));
    }

    @Test
    void testUpdate() throws Exception {
        when(userService.get(anyLong()))
                .thenReturn(user3);
        when(userService.save(any()))
                .thenReturn(user3);
        mvc.perform(patch("/users/{id}", user3.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(user3.getName())))
                .andExpect(jsonPath("$.email", is(user3.getEmail())));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(userService)
                .delete(user3.getId());
        mvc.perform(delete("/users/{id}", user3.getId()))
                .andExpect(status().isOk());
        verify(userService, times(1)).delete(user3.getId());
    }
}