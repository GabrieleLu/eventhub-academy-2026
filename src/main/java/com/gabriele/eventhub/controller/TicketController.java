package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.BookingRequestDTO;
import com.gabriele.eventhub.dto.TicketResponseDTO;
import com.gabriele.eventhub.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // POST /events/{id}/book
    @PostMapping("/events/{id}/book")
    public ResponseEntity<TicketResponseDTO> bookTicket(
            @PathVariable Long id,
            @Valid @RequestBody BookingRequestDTO dto) {

        TicketResponseDTO response = ticketService.bookTicket(id, dto);
        return ResponseEntity.ok(response);
    }

    // DELETE /tickets/{id}
    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<Void> cancelTicket(@PathVariable Long id) {
        ticketService.cancelTicket(id);
        return ResponseEntity.noContent().build();
    }

    // GET /tickets/my
    @GetMapping("/tickets/my")
    public ResponseEntity<List<TicketResponseDTO>> getMyTickets() {
        return ResponseEntity.ok(ticketService.getMyTickets());
    }

    // GET controllare i posti disponibili  con postamman 
    @GetMapping("/events/{id}/seats")
    public ResponseEntity<Integer> getAvailableSeats(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getAvailableSeats(id));
    }
}