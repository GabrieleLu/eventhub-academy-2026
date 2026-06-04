package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.VenueRequestDTO;
import com.gabriele.eventhub.dto.VenueResponseDTO;
import com.gabriele.eventhub.service.VenueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public ResponseEntity<List<VenueResponseDTO>> findAll() {
        return ResponseEntity.ok(venueService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(venueService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VenueResponseDTO> create(@Valid @RequestBody VenueRequestDTO dto) {
        return ResponseEntity.ok(venueService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueResponseDTO> update(@PathVariable Long id, @Valid @RequestBody VenueRequestDTO dto) {
        return ResponseEntity.ok(venueService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        venueService.delete(id);
        return ResponseEntity.noContent().build();
    }
}