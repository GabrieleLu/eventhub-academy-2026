package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.BookingRequestDTO;
import com.gabriele.eventhub.dto.TicketResponseDTO;
import com.gabriele.eventhub.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Tickets", description = "Gestione delle prenotazioni (ticket)")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/events/{id}/book")
    @Operation(summary = "Prenota un biglietto per un evento",
               description = "Crea una prenotazione per l'evento specificato. Solo utenti autenticati. Richiede un ticket type (STANDARD o VIP).")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Prenotazione effettuata con successo"),
        @ApiResponse(responseCode = "400", description = "Dati non validi"),
        @ApiResponse(responseCode = "409", description = "Conflitto: evento passato, hai già un ticket, o nessun posto disponibile"),
        @ApiResponse(responseCode = "404", description = "Evento non trovato")
    })
    public ResponseEntity<TicketResponseDTO> bookTicket(
            @Parameter(description = "ID dell'evento") @PathVariable Long id,
            @Valid @RequestBody BookingRequestDTO dto) {
        TicketResponseDTO response = ticketService.bookTicket(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/tickets/{id}")
    @Operation(summary = "Cancella una prenotazione",
               description = "Cancella il ticket specificato. Solo il proprietario del ticket può cancellarlo.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Prenotazione cancellata con successo"),
        @ApiResponse(responseCode = "404", description = "Ticket non trovato"),
        @ApiResponse(responseCode = "409", description = "Non autorizzato o ticket già cancellato")
    })
    public ResponseEntity<Void> cancelTicket(@Parameter(description = "ID del ticket") @PathVariable Long id) {
        ticketService.cancelTicket(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tickets/my")
    @Operation(summary = "Recupera i tuoi ticket",
               description = "Restituisce lista di tutti i ticket (ACTIVE e CANCELLED) dell'utente loggato.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista di ticket recuperata"),
        @ApiResponse(responseCode = "401", description = "Non autenticato")
    })
    public ResponseEntity<List<TicketResponseDTO>> getMyTickets() {
        return ResponseEntity.ok(ticketService.getMyTickets());
    }

    @GetMapping("/events/{id}/seats")
    @Operation(summary = "Recupera posti disponibili per un evento",
               description = "Restituisce il numero di posti liberi (capienza venue - ticket ACTIVE).")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Numero di posti disponibili"),
        @ApiResponse(responseCode = "404", description = "Evento non trovato")
    })
    public ResponseEntity<Integer> getAvailableSeats(@Parameter(description = "ID dell'evento") @PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getAvailableSeats(id));
    }
}