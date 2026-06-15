package com.gabriele.eventhub.service;

import com.gabriele.eventhub.dto.FeedbackRequestDTO;
import com.gabriele.eventhub.dto.FeedbackResponseDTO;
import com.gabriele.eventhub.dto.RatingResponseDTO;
import com.gabriele.eventhub.entity.*;
import com.gabriele.eventhub.exception.ResourceNotFoundException;
import com.gabriele.eventhub.exception.ValidationException;
import com.gabriele.eventhub.repository.EventRepository;
import com.gabriele.eventhub.repository.FeedbackRepository;
import com.gabriele.eventhub.repository.TicketRepository;
import com.gabriele.eventhub.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public FeedbackService(FeedbackRepository feedbackRepository,
                           EventRepository eventRepository,
                           UserRepository userRepository,
                           TicketRepository ticketRepository) {
        this.feedbackRepository = feedbackRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    // --- crea feedback ---
    public FeedbackResponseDTO createFeedback(Long eventId, FeedbackRequestDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));

        // evento deve essere concluso
        if (event.getEndDate().isAfter(LocalDateTime.now())) {
            throw new ValidationException("Non puoi lasciare feedback a un evento non ancora concluso");
        }

        // utente deve avere ticket ACTIVE
        boolean hasActiveTicket = ticketRepository.existsByUserIdAndEventIdAndStatus(
                user.getId(), eventId, TicketStatus.ACTIVE);

        if (!hasActiveTicket) {
            throw new ValidationException("Devi avere un biglietto attivo per lasciare feedback");
        }

        // non può avere già lasciato feedback
        if (feedbackRepository.existsByUserIdAndEventId(user.getId(), eventId)) {
            throw new ValidationException("Hai già lasciato un feedback per questo evento");
        }

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setEvent(event);
        feedback.setRating(dto.getRating());
        feedback.setComment(dto.getComment());

        feedbackRepository.save(feedback);
        return toDTO(feedback);
    }

    // --- leggi tutti i feedback di un evento ---
    public List<FeedbackResponseDTO> getFeedbacksByEvent(Long eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));

        return feedbackRepository.findByEventId(eventId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // --- elimina feedback ---
    public void deleteFeedback(Long feedbackId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback non trovato"));

        if (!feedback.getUser().getEmail().equals(email)) {
            throw new ValidationException("Non puoi eliminare il feedback di un altro utente");
        }

        feedbackRepository.deleteById(feedbackId);
    }

    // --- calcola media voti ---
    public RatingResponseDTO getEventRating(Long eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));

        List<Feedback> feedbacks = feedbackRepository.findByEventId(eventId);

        if (feedbacks.isEmpty()) {
            return new RatingResponseDTO(0.0, 0);
        }

        double average = feedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);

        return new RatingResponseDTO(average, feedbacks.size());
    }

    // --- mapper ---
    private FeedbackResponseDTO toDTO(Feedback feedback) {
        FeedbackResponseDTO dto = new FeedbackResponseDTO();
        dto.setId(feedback.getId());
        dto.setEventTitle(feedback.getEvent().getTitle());
        dto.setUsername(feedback.getUser().getEmail());
        dto.setRating(feedback.getRating());
        dto.setComment(feedback.getComment());
        return dto;
    }
}