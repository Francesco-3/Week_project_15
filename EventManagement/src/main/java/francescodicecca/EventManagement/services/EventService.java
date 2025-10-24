package francescodicecca.EventManagement.services;

import francescodicecca.EventManagement.entities.Event;
import francescodicecca.EventManagement.entities.User;
import francescodicecca.EventManagement.exceptions.NotFoundException;
import francescodicecca.EventManagement.repositories.EventRepository;
import francescodicecca.EventManagement.payloads.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(EventDTO dto, User organizer) {
        Event event = new Event();
        event.setTitle(dto.title());
        event.setDescription(dto.description());
        event.setDate(dto.date());
        event.setPlace(dto.place());
        event.setAvailablePlaces(dto.availablePlaces());
        event.setOrganizer(organizer);

        return eventRepository.save(event);
    }

    public Event updateEvent(UUID eventId, EventDTO dto, User organizer) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId));

        if (!event.getOrganizer().getId().equals(organizer.getId())) {
            throw new SecurityException("Non sei autorizzato a modificare questo evento");
        }

        event.setTitle(dto.title());
        event.setDescription(dto.description());
        event.setDate(dto.date());
        event.setPlace(dto.place());
        event.setAvailablePlaces(dto.availablePlaces());

        return eventRepository.save(event);
    }

    public void deleteEvent(UUID eventId, User organizer) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId));

        if (!event.getOrganizer().getId().equals(organizer.getId())) {
            throw new SecurityException("Non sei autorizzato a eliminare questo evento");
        }

        eventRepository.delete(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(UUID eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId));
    }

    public List<Event> getEventsByOrganizer(User organizer) {
        return eventRepository.findByOrganizer(organizer);
    }
}
