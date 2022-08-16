package ru.practicum.shareit.booking;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.ModelsRepForTests.*;

@AutoConfigureMockMvc
@SpringBootTest
public class BookingControllerTest {

    @MockBean
    private BookingService bookingService;

    @MockBean
    private UserService service;

    @MockBean
    private ItemService itemService;

    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    static final String HEADER_REQUEST = "X-Sharer-User-Id";

    @Test
    void testCreate() throws Exception {
        when(bookingService.save(any()))
                .thenReturn(booking);

        mvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(booking))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HEADER_REQUEST, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.name", is(booking.getItem().getName())));
    }

    @Test
    void testUpdate() throws Exception {
        when(bookingService.get(anyLong()))
                .thenReturn(booking);

        mvc.perform(get("/bookings/{id}", booking.getId())
                        .content(mapper.writeValueAsString(booking))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HEADER_REQUEST, user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.name", is(booking.getItem().getName())));
    }

    @Test
    void testGet() throws Exception {
        when(bookingService.get(anyLong()))
                .thenReturn(booking);

        mvc.perform(get("/bookings/{bookingId}", booking.getId())
                        .content(mapper.writeValueAsString(booking))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HEADER_REQUEST, user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item.name", is(item.getName())));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(bookingService)
                .delete(booking.getId());
        mvc.perform(delete("/bookings/{id}", booking.getId()))
                .andExpect(status().isOk());
        verify(bookingService, times(1)).delete(booking.getId());
    }
}
