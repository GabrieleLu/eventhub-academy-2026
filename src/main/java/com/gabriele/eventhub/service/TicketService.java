package com.gabriele.eventhub.service;

import com.gabriele.eventhub.dto.BookingRequestDTO;
import com.gabriele.eventhub.dto.TicketResponseDTO;
import com.gabriele.eventhub.entity.*;
import com.gabriele.eventhub.exception.ResourceNotFoundException;
import com.gabriele.eventhub.exception.ValidationException;
import com.gabriele.eventhub.repository.EventRepository;
import com.gabriele.eventhub.repository.TicketRepository;
import com.gabriele.eventhub.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository,
                         EventRepository eventRepository,
                         UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    // --- logica availableSeats ---
    public int getAvailableSeats(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));

        int capacity = event.getVenue().getCapacity();
        long activeTickets = ticketRepository.countByEventIdAndStatus(eventId, TicketStatus.ACTIVE);

        return (int) (capacity - activeTickets);
    }

    // --- prenotazione ---
    public TicketResponseDTO bookTicket(Long eventId, BookingRequestDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));
        
        if (event.getEndDate().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Non puoi prenotare un evento già terminato");
        }

        
        boolean alreadyBooked = ticketRepository.existsByUserIdAndEventIdAndStatus(
                user.getId(), eventId, TicketStatus.ACTIVE);

        if (alreadyBooked) {
            throw new ValidationException("Hai già una prenotazione attiva per questo evento");
        }
        
        if (getAvailableSeats(eventId) <= 0) {
            throw new ValidationException("Nessun posto disponibile per questo evento");
        }
        
        
        

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setEvent(event);
        ticket.setType(dto.getType());
        ticket.setStatus(TicketStatus.ACTIVE);

        if (dto.getType() == TicketType.VIP) {
            ticket.setPricePaid(event.getVipPrice());
        } else {
            ticket.setPricePaid(event.getStandardPrice());
        }
        ticketRepository.save(ticket);
        return toDTO(ticket);
    }

    // --- cancellazione ---
    public void cancelTicket(Long ticketId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket non trovato"));

        if (!ticket.getUser().getEmail().equals(email)) {
            throw new ValidationException("Non puoi cancellare il ticket di un altro utente");
        }

        if (ticket.getStatus() == TicketStatus.CANCELLED) {
            throw new ValidationException("Il ticket è già stato cancellato");
        }

        ticket.setStatus(TicketStatus.CANCELLED);
        ticketRepository.save(ticket);
    }

    // --- ticket dell'utente loggato ---
    public List<TicketResponseDTO> getMyTickets() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));

        return ticketRepository.findByUserId(user.getId())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // --- mapper ---
    private TicketResponseDTO toDTO(Ticket ticket) {
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setId(ticket.getId());
        dto.setEventTitle(ticket.getEvent().getTitle());
        dto.setUsername(ticket.getUser().getEmail());
        dto.setType(ticket.getType());
        dto.setPricePaid(ticket.getPricePaid());
        dto.setStatus(ticket.getStatus());
        return dto;
    }
}