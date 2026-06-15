package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.SpeakerRequestDTO;
import com.gabriele.eventhub.dto.SpeakerResponseDTO;
import com.gabriele.eventhub.service.SpeakerService;
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
@RequestMapping("/admin/speakers")
@Tag(name = "Speakers", description = "Gestione dei relatori (solo ADMIN)")
public class SpeakerController {

    private final SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping
    @Operation(summary = "Recupera tutti i relatori",
               description = "Restituisce lista di tutti i relatori registrati. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista di relatori recuperata"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<List<SpeakerResponseDTO>> findAll() {
        return ResponseEntity.ok(speakerService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera un relatore per ID",
               description = "Restituisce i dettagli di uno specifico relatore. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatore trovato"),
        @ApiResponse(responseCode = "404", description = "Relatore non trovato"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<SpeakerResponseDTO> findById(@Parameter(description = "ID del relatore") @PathVariable Long id) {
        return ResponseEntity.ok(speakerService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crea un nuovo relatore",
               description = "Aggiunge un nuovo relatore al sistema. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatore creato"),
        @ApiResponse(responseCode = "400", description = "Dati non validi"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<SpeakerResponseDTO> create(@Valid @RequestBody SpeakerRequestDTO dto) {
        return ResponseEntity.ok(speakerService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifica un relatore",
               description = "Aggiorna i dati di un relatore. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatore modificato"),
        @ApiResponse(responseCode = "404", description = "Relatore non trovato"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<SpeakerResponseDTO> update(
            @Parameter(description = "ID del relatore") @PathVariable Long id,
            @Valid @RequestBody SpeakerRequestDTO dto) {
        return ResponseEntity.ok(speakerService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un relatore",
               description = "Rimuove un relatore dal sistema. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Relatore eliminato"),
        @ApiResponse(responseCode = "404", description = "Relatore non trovato"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID del relatore") @PathVariable Long id) {
        speakerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}