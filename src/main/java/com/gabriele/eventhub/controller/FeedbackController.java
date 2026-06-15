package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.FeedbackRequestDTO;
import com.gabriele.eventhub.dto.FeedbackResponseDTO;
import com.gabriele.eventhub.dto.RatingResponseDTO;
import com.gabriele.eventhub.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // POST /events/{id}/feedback
    @PostMapping("/events/{id}/feedback")
    public ResponseEntity<FeedbackResponseDTO> createFeedback(
            @PathVariable Long id,
            @Valid @RequestBody FeedbackRequestDTO dto) {

        FeedbackResponseDTO response = feedbackService.createFeedback(id, dto);
        return ResponseEntity.ok(response);
    }

    // GET /events/{id}/feedback
    @GetMapping("/events/{id}/feedback")
    public ResponseEntity<List<FeedbackResponseDTO>> getFeedbacksByEvent(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByEvent(id));
    }

    // DELETE /feedback/{id}
    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }

    // GET /events/{id}/rating
    @GetMapping("/events/{id}/rating")
    public ResponseEntity<RatingResponseDTO> getEventRating(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getEventRating(id));
    }
}