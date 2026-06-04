package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.SpeakerRequestDTO;
import com.gabriele.eventhub.dto.SpeakerResponseDTO;
import com.gabriele.eventhub.service.SpeakerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/speakers")
public class SpeakerController {

    private final SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping
    public ResponseEntity<List<SpeakerResponseDTO>> findAll() {
        return ResponseEntity.ok(speakerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpeakerResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(speakerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SpeakerResponseDTO> create(@Valid @RequestBody SpeakerRequestDTO dto) {
        return ResponseEntity.ok(speakerService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpeakerResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SpeakerRequestDTO dto) {
        return ResponseEntity.ok(speakerService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        speakerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}