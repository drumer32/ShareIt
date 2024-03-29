package ru.practicum.shareitserver.booking.model;

import lombok.*;
import org.hibernate.Hibernate;
import ru.practicum.shareitserver.item.model.Item;
import ru.practicum.shareitserver.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "start_date_time")
    @NotNull LocalDateTime start;

    @Column(name = "end_date_time")
    @NotNull LocalDateTime end;

    @ManyToOne
    Item item;

    @ManyToOne
    User booker;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Booking booking = (Booking) o;
        return id != null && Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
