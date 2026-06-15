package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.EventRequestDTO;
import com.gabriele.eventhub.dto.EventResponseDTO;
import com.gabriele.eventhub.entity.Role;
import com.gabriele.eventhub.entity.User;
import com.gabriele.eventhub.exception.ResourceNotFoundException;
import com.gabriele.eventhub.exception.ValidationException;
import com.gabriele.eventhub.repository.UserRepository;
import com.gabriele.eventhub.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Events", description = "Gestione degli eventi")
public class EventController {

    private final EventService eventService;
    private final UserRepository userRepository;

    public EventController(EventService eventService, UserRepository userRepository) {
        this.eventService = eventService;
        this.userRepository = userRepository;
    }

    @GetMapping
    @Operation(summary = "Recupera tutti gli eventi con paginazione e filtri",
               description = "Restituisce una lista paginata di eventi. Supporta filtri per data, venue, organizer e tag. Se non specificati i filtri, restituisce tutti gli eventi ordinati.")
    @Parameters({
        @Parameter(name = "page", description = "Numero della pagina (0-indexed)", example = "0"),
        @Parameter(name = "size", description = "Numero di elementi per pagina", example = "10"),
        @Parameter(name = "sort", description = "Ordinamento: es. 'startDate,desc' o 'standardPrice,asc'", example = "startDate,desc"),
        @Parameter(name = "startDate", description = "Data inizio filtro (ISO format)"),
        @Parameter(name = "endDate", description = "Data fine filtro (ISO format)"),
        @Parameter(name = "venueId", description = "ID della venue"),
        @Parameter(name = "organizerId", description = "ID dell'organizzatore"),
        @Parameter(name = "tagId", description = "ID del tag")
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista di eventi recuperata con successo"),
        @ApiResponse(responseCode = "400", description = "Parametri di richiesta non validi")
    })
    public ResponseEntity<Page<EventResponseDTO>> findAll(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Long venueId,
            @RequestParam(required = false) Long organizerId,
            @RequestParam(required = false) Long tagId,
            Pageable pageable) {
        
        if (startDate != null || endDate != null || venueId != null || organizerId != null || tagId != null) {
            List<EventResponseDTO> events = eventService.findByFilters(startDate, endDate, venueId, organizerId, tagId);
            return ResponseEntity.ok(null);
        }
        
        return ResponseEntity.ok(eventService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera un evento per ID",
               description = "Restituisce i dettagli di un singolo evento.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evento trovato"),
        @ApiResponse(responseCode = "404", description = "Evento non trovato")
    })
    public ResponseEntity<EventResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crea un nuovo evento",
               description = "Solo gli ORGANIZER possono creare eventi. Richiede title, description, date, prezzi e venue validi.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evento creato con successo"),
        @ApiResponse(responseCode = "400", description = "Dati non validi"),
        @ApiResponse(responseCode = "409", description = "Conflitto: prezzi incoerenti o date non valide")
    })
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
    @Operation(summary = "Modifica un evento",
               description = "Solo l'organizzatore può modificare il proprio evento.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evento modificato con successo"),
        @ApiResponse(responseCode = "404", description = "Evento non trovato"),
        @ApiResponse(responseCode = "409", description = "Non autorizzato a modificare l'evento")
    })
    public ResponseEntity<EventResponseDTO> update(
            @PathVariable Long id,
            Authentication authentication,
            @Valid @RequestBody EventRequestDTO dto) {
        return ResponseEntity.ok(eventService.update(id, authentication.getName(), dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un evento",
               description = "Solo l'organizzatore può eliminare il proprio evento.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Evento eliminato con successo"),
        @ApiResponse(responseCode = "404", description = "Evento non trovato"),
        @ApiResponse(responseCode = "409", description = "Non autorizzato a eliminare l'evento")
    })
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication) {
        eventService.delete(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}