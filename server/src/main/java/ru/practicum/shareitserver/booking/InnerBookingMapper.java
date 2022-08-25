package ru.practicum.shareitserver.booking;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareitserver.booking.dto.InnerBookingDto;
import ru.practicum.shareitserver.booking.model.Booking;

@Component
@AllArgsConstructor
public class InnerBookingMapper {

    public InnerBookingDto convert(Booking booking) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(booking, InnerBookingDto.class);
    }
}
