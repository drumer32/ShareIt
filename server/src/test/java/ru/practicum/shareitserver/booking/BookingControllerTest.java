package ru.practicum.shareitserver.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareitserver.booking.service.BookingService;
import ru.practicum.shareitserver.item.repository.ItemRepository;
import ru.practicum.shareitserver.user.repository.UserRepository;
import ru.practicum.shareitserver.user.service.UserService;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareitserver.ModelsRepForTests.*;

@AutoConfigureMockMvc
@SpringBootTest
public class BookingControllerTest {

    @MockBean
    private BookingService bookingService;

    @MockBean
    private UserService userService;

    @MockBean
    ItemRepository repository;

    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    static final String HEADER_REQUEST = "X-Sharer-User-Id";

    @Test
    void testCreate() throws Exception {
        userService.save(user);
        repository.save(item);

        when(bookingService.save(any()))
                .thenReturn(booking);

        mvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HEADER_REQUEST, user.getId()))
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
