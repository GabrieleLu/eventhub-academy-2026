package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.EventRequestDTO;
import com.gabriele.eventhub.dto.EventResponseDTO;
import com.gabriele.eventhub.entity.Role;
import com.gabriele.eventhub.entity.User;
import com.gabriele.eventhub.exception.ResourceNotFoundException;
import com.gabriele.eventhub.exception.ValidationException;
import com.gabriele.eventhub.repository.UserRepository;
import com.gabriele.eventhub.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final UserRepository userRepository;

    public EventController(EventService eventService,UserRepository userRepository) {
        this.eventService = eventService;
        this.userRepository= userRepository;
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> findAll() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> create(
            Authentication authentication,
            @Valid @RequestBody EventRequestDTO dto) {
        
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
        
        if (user.getRole() != Role.ORGANIZER) {
            throw new ValidationException("Solo gli ORGANIZER possono creare eventi");
        }
        
        return ResponseEntity.ok(eventService.create(authentication.getName(), dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> update(
            @PathVariable Long id,
            Authentication authentication,
            @Valid @RequestBody EventRequestDTO dto) {
        return ResponseEntity.ok(eventService.update(id, authentication.getName(), dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication) {
        eventService.delete(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}