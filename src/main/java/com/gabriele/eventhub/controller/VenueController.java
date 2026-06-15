package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.VenueRequestDTO;
import com.gabriele.eventhub.dto.VenueResponseDTO;
import com.gabriele.eventhub.service.VenueService;
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
@RequestMapping("/admin/venues")
@Tag(name = "Venues", description = "Gestione delle sedi (solo ADMIN)")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    @Operation(summary = "Recupera tutte le sedi",
               description = "Restituisce lista di tutte le sedi disponibili. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista di sedi recuperata"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<List<VenueResponseDTO>> findAll() {
        return ResponseEntity.ok(venueService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera una sede per ID",
               description = "Restituisce i dettagli di una specifica sede. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sede trovata"),
        @ApiResponse(responseCode = "404", description = "Sede non trovata"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<VenueResponseDTO> findById(@Parameter(description = "ID della sede") @PathVariable Long id) {
        return ResponseEntity.ok(venueService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crea una nuova sede",
               description = "Aggiunge una nuova sede al sistema. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sede creata"),
        @ApiResponse(responseCode = "400", description = "Dati non validi"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<VenueResponseDTO> create(@Valid @RequestBody VenueRequestDTO dto) {
        return ResponseEntity.ok(venueService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifica una sede",
               description = "Aggiorna i dati di una sede. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sede modificata"),
        @ApiResponse(responseCode = "404", description = "Sede non trovata"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<VenueResponseDTO> update(
            @Parameter(description = "ID della sede") @PathVariable Long id,
            @Valid @RequestBody VenueRequestDTO dto) {
        return ResponseEntity.ok(venueService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una sede",
               description = "Rimuove una sede dal sistema. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Sede eliminata"),
        @ApiResponse(responseCode = "404", description = "Sede non trovata"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID della sede") @PathVariable Long id) {
        venueService.delete(id);
        return ResponseEntity.noContent().build();
    }
}