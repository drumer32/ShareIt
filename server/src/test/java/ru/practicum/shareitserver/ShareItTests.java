package ru.practicum.shareitserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

public class ShareItTests {

	public static final ObjectMapper objectMapper = JsonMapper.builder()
			.findAndAddModules()
			.build();

	@Test
	void contextLoads() {
		ShareItServer.main(new String[]{});
	}

}
