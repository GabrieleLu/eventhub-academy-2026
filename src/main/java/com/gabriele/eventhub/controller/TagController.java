package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.TagRequestDTO;
import com.gabriele.eventhub.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
@Tag(name = "Tags", description = "Gestione dei tag per categorizzare eventi")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @Operation(summary = "Crea un nuovo tag",
               description = "Aggiunge un nuovo tag al sistema per categorizzare gli eventi.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tag creato con successo"),
        @ApiResponse(responseCode = "400", description = "Dati non validi")
    })
    public ResponseEntity<com.gabriele.eventhub.entity.Tag> create(@Valid @RequestBody TagRequestDTO dto) {
        return ResponseEntity.ok(tagService.create(dto));
    }
}