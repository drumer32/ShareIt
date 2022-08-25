package ru.practicum.shareitgateway.item;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareitgateway.client.BaseClient;
import ru.practicum.shareitgateway.exceptions.ObjectNotValidException;
import ru.practicum.shareitgateway.item.dto.CreateCommentDto;
import ru.practicum.shareitgateway.item.dto.CreateItemDto;
import ru.practicum.shareitgateway.item.dto.UpdateItemDto;
import ru.practicum.shareitgateway.item.validation.ItemUpdateValidator;

@Service
public class ItemClient extends BaseClient {

    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(
        @Value("${shareit-server.url}") String serverUrl,
        @Value("${app.auth-header}") String authHeader,
        RestTemplateBuilder builder
    ) {
        super(
            builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .build(),
            authHeader
        );
    }

    public Object create(long userId, CreateItemDto requestDto) {
        return post("", userId, requestDto);
    }

    public Object update(long userId, long itemId, UpdateItemDto dto) throws ObjectNotValidException {
        ItemUpdateValidator validator = new ItemUpdateValidator();
        if (validator.isValid(dto)) {
            return patch("/" + itemId, userId, null, dto);
        } else throw new ObjectNotValidException();
    }

    public Object get(long userId, long itemId) {
        return get("/" + itemId, userId);
    }

    public Object getAllOfUser(long userId, int from, int size) {
        Map<String, Object> parameters = Map.of(
            "from", from,
            "size", size
        );
        return get("", userId, parameters);
    }

    public Object search(String text, int from, int size) {
        Map<String, Object> parameters = Map.of(
            "text", text,
            "from", from,
            "size", size
        );
        return get("/search", null, parameters);
    }

    public Object addComment(long userId, long itemId, CreateCommentDto dto) {
        return post("/" + itemId + "/comment", userId, dto);
    }
}
