package francescodicecca.EventManagement.controllers;

import francescodicecca.EventManagement.entities.Event;
import francescodicecca.EventManagement.entities.User;
import francescodicecca.EventManagement.payloads.EventDTO;
import francescodicecca.EventManagement.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    // Lista di tutti gli eventi
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // Dettaglio evento
    @GetMapping("/{id}")
    public Event getEvent(@PathVariable UUID id) {
        return eventService.getEventById(id);
    }

    // Creazione evento (solo ORGANIZER)
    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Event createEvent(@RequestBody @Validated EventDTO dto,
                             @AuthenticationPrincipal User organizer) {
        return eventService.createEvent(dto, organizer);
    }

    // Modifica evento (solo organizzatore dell'evento)
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Event updateEvent(@PathVariable UUID id,
                             @RequestBody @Validated EventDTO dto,
                             @AuthenticationPrincipal User organizer) {
        return eventService.updateEvent(id, dto, organizer);
    }

    // Eliminazione evento (solo organizzatore dell'evento)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public void deleteEvent(@PathVariable UUID id,
                            @AuthenticationPrincipal User organizer) {
        eventService.deleteEvent(id, organizer);
    }

    // Lista eventi creati dall'organizzatore
    @GetMapping("/mine")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public List<Event> getMyEvents(@AuthenticationPrincipal User organizer) {
        return eventService.getEventsByOrganizer(organizer);
    }
}
