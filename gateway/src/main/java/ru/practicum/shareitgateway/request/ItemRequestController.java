package ru.practicum.shareitgateway.request;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareitgateway.request.dto.CreateItemRequestDto;

@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
@Validated
public class ItemRequestController {

    private final RequestClient client;

    private static final String HEADER_REQUEST = "X-Sharer-User-Id";

    @PostMapping
    public Object create(@RequestHeader(HEADER_REQUEST) long userId,
        @RequestBody @Valid CreateItemRequestDto dto
    ) {
        return client.create(userId, dto);
    }

    @GetMapping("/all")
    public Object getAllRequestsOfUser(@RequestHeader(HEADER_REQUEST) long userId,
        @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
        @RequestParam(name = "size", defaultValue = "10") @Positive int size
    ) {
        return client.getAllRequestsOfUser(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public Object get(@RequestHeader(HEADER_REQUEST) long userId,
                      @PathVariable("requestId") long requestId
    ) {
        return client.get(userId, requestId);
    }
}
