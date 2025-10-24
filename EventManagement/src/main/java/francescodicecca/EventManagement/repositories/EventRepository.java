package francescodicecca.EventManagement.repositories;

import francescodicecca.EventManagement.entities.Event;
import francescodicecca.EventManagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByOrganizer(User organizer);
}
