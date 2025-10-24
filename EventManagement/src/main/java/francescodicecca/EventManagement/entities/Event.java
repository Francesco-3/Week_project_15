package francescodicecca.EventManagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String title;

    @Column(length = 1000)
    private String description;

    private LocalDateTime date;

    private String place;

    private Integer availablePlaces;

    @ManyToOne
    @JoinColumn(name = "id_organizer")
    private User organizer;
}
