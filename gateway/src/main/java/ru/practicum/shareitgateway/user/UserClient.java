package ru.practicum.shareitgateway.user;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareitgateway.client.BaseClient;
import ru.practicum.shareitgateway.exceptions.ObjectNotValidException;
import ru.practicum.shareitgateway.user.dto.CreateUserDto;
import ru.practicum.shareitgateway.user.dto.UpdateUserDto;
import ru.practicum.shareitgateway.user.validation.UserUpdateValidator;

@Service
public class UserClient extends BaseClient {

    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(
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

    public Object createUser(CreateUserDto dto) {
        return post("", null, dto);
    }

    public Object updateUser(long userId, UpdateUserDto dto) throws ObjectNotValidException {
        UserUpdateValidator validator = new UserUpdateValidator();
        if (validator.isValid(dto)) {
        return patch("/" + userId, null, null, dto);
        } else throw new ObjectNotValidException();
    }

    public Object getUserById(long userId) {
        return get("/" + userId, userId);
    }

    public Object getAllUsers(int from, int size) {
        Map<String, Object> parameters = Map.of(
            "from", from,
            "size", size
        );
        return get("", null, parameters);
    }

    public void deleteUser(long userId) {
        delete("/" + userId, null);
    }
}
