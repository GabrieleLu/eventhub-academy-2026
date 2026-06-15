package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.FeedbackRequestDTO;
import com.gabriele.eventhub.dto.FeedbackResponseDTO;
import com.gabriele.eventhub.dto.RatingResponseDTO;
import com.gabriele.eventhub.service.FeedbackService;
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
@Tag(name = "Feedback", description = "Gestione dei feedback e delle recensioni")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/events/{id}/feedback")
    @Operation(summary = "Crea una recensione per un evento",
               description = "Lascia una recensione con voto (1-5) e commento. Solo se l'evento è concluso e possiedi un ticket ACTIVE.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Feedback creato con successo"),
        @ApiResponse(responseCode = "400", description = "Dati non validi"),
        @ApiResponse(responseCode = "409", description = "Conflitto: evento non concluso, nessun ticket, o feedback già lasciato"),
        @ApiResponse(responseCode = "404", description = "Evento non trovato")
    })
    public ResponseEntity<FeedbackResponseDTO> createFeedback(
            @Parameter(description = "ID dell'evento") @PathVariable Long id,
            @Valid @RequestBody FeedbackRequestDTO dto) {
        FeedbackResponseDTO response = feedbackService.createFeedback(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/events/{id}/feedback")
    @Operation(summary = "Recupera tutti i feedback di un evento",
               description = "Restituisce lista di tutte le recensioni dell'evento.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista di feedback recuperata"),
        @ApiResponse(responseCode = "404", description = "Evento non trovato")
    })
    public ResponseEntity<List<FeedbackResponseDTO>> getFeedbacksByEvent(
            @Parameter(description = "ID dell'evento") @PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByEvent(id));
    }

    @DeleteMapping("/feedback/{id}")
    @Operation(summary = "Elimina una recensione",
               description = "Elimina il feedback specificato. Solo il proprietario può eliminarlo.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Feedback eliminato con successo"),
        @ApiResponse(responseCode = "404", description = "Feedback non trovato"),
        @ApiResponse(responseCode = "409", description = "Non autorizzato")
    })
    public ResponseEntity<Void> deleteFeedback(@Parameter(description = "ID del feedback") @PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/events/{id}/rating")
    @Operation(summary = "Recupera la media voti di un evento",
               description = "Restituisce media dei voti e numero totale di feedback.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rating recuperato"),
        @ApiResponse(responseCode = "404", description = "Evento non trovato")
    })
    public ResponseEntity<RatingResponseDTO> getEventRating(
            @Parameter(description = "ID dell'evento") @PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getEventRating(id));
    }
}