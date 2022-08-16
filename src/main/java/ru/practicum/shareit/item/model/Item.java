package ru.practicum.shareit.item.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private boolean available;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @Nullable
    private Long itemRequestId;

    @Transient
    private Booking lastBooking;

    @Transient
    private Booking nextBooking;

    public Item(Long id, String name, String description, boolean available,
                User owner, ArrayList<Comment> comments, Long itemRequestId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
        this.comments = comments;
        this.itemRequestId = itemRequestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        return id != null && Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
