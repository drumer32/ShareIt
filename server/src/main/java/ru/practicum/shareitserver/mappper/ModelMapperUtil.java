package ru.practicum.shareitserver.mappper;

import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import org.modelmapper.ModelMapper;

@Component
public class ModelMapperUtil extends ModelMapper{

    public ModelMapperUtil() {
        this.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);
    }
}
