package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.InnerBookingDto;
import ru.practicum.shareit.booking.model.Booking;

@Component
@AllArgsConstructor
public class InnerBookingMapper {

    public InnerBookingDto convert(Booking booking) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(booking, InnerBookingDto.class);
    }
}
