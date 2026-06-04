package com.gabriele.eventhub.service;

import com.gabriele.eventhub.dto.EventRequestDTO;
import com.gabriele.eventhub.dto.EventResponseDTO;
import com.gabriele.eventhub.entity.Event;
import com.gabriele.eventhub.entity.Tag;
import com.gabriele.eventhub.entity.User;
import com.gabriele.eventhub.entity.Venue;
import com.gabriele.eventhub.exception.ResourceNotFoundException;
import com.gabriele.eventhub.exception.ValidationException;
import com.gabriele.eventhub.repository.EventRepository;
import com.gabriele.eventhub.repository.TagRepository;
import com.gabriele.eventhub.repository.UserRepository;
import com.gabriele.eventhub.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;
    private final TagRepository tagRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, 
                        VenueRepository venueRepository, TagRepository tagRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.venueRepository = venueRepository;
        this.tagRepository = tagRepository;
    }

    public List<EventResponseDTO> findAll() {
        List<Event> events = eventRepository.findAll();
        List<EventResponseDTO> result = new ArrayList<>();
        for (Event event : events) {
            result.add(toDTO(event));
        }
        return result;
    }

    public EventResponseDTO findById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));
        return toDTO(event);
    }

    public EventResponseDTO create(String organizerEmail, EventRequestDTO dto) {
        User organizer = userRepository.findByEmail(organizerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Organizzatore non trovato"));

        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Sede non trovata"));

        if (dto.getVipPrice().compareTo(dto.getStandardPrice()) <= 0) {
            throw new ValidationException("Il prezzo VIP deve essere maggiore del prezzo standard");
        }

        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setStandardPrice(dto.getStandardPrice());
        event.setVipPrice(dto.getVipPrice());
        event.setStatus(dto.getStatus());
        event.setOrganizer(organizer);
        event.setVenue(venue);

        if (dto.getTagIds() != null && !dto.getTagIds().isEmpty()) {
            List<Tag> tags = new ArrayList<>();
            for (Long tagId : dto.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tag non trovato"));
                tags.add(tag);
            }
            event.setTags(tags);
        }

        eventRepository.save(event);
        return toDTO(event);
    }

    public EventResponseDTO update(Long id, String organizerEmail, EventRequestDTO dto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));

        User currentUser = userRepository.findByEmail(organizerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));

        if (!event.getOrganizer().getId().equals(currentUser.getId())) {
            throw new ValidationException("Puoi modificare solo i tuoi eventi");
        }

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setStandardPrice(dto.getStandardPrice());
        event.setVipPrice(dto.getVipPrice());
        event.setStatus(dto.getStatus());

        if (dto.getTagIds() != null) {
            List<Tag> tags = new ArrayList<>();
            for (Long tagId : dto.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tag non trovato"));
                tags.add(tag);
            }
            event.setTags(tags);
        }

        eventRepository.save(event);
        return toDTO(event);
    }

    public void delete(Long id, String organizerEmail) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));

        User currentUser = userRepository.findByEmail(organizerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));

        if (!event.getOrganizer().getId().equals(currentUser.getId())) {
            throw new ValidationException("Puoi eliminare solo i tuoi eventi");
        }

        eventRepository.deleteById(id);
    }

    private EventResponseDTO toDTO(Event event) {
        EventResponseDTO dto = new EventResponseDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setStartDate(event.getStartDate());
        dto.setEndDate(event.getEndDate());
        dto.setStandardPrice(event.getStandardPrice());
        dto.setVipPrice(event.getVipPrice());
        dto.setStatus(event.getStatus());
        dto.setOrganizerEmail(event.getOrganizer().getEmail());
        dto.setVenueName(event.getVenue().getName());
        
        if (event.getTags() != null) {
            dto.setTagNames(event.getTags().stream()
                    .map(Tag::getName)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
}