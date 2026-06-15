package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.ProfileRequestDTO;
import com.gabriele.eventhub.dto.ProfileResponseDTO;
import com.gabriele.eventhub.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/me")
@Tag(name = "Profile", description = "Gestione del profilo utente personale")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    @Operation(summary = "Recupera il tuo profilo",
               description = "Restituisce i dettagli del profilo dell'utente loggato.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profilo recuperato"),
        @ApiResponse(responseCode = "404", description = "Profilo non trovato"),
        @ApiResponse(responseCode = "401", description = "Non autenticato")
    })
    public ResponseEntity<ProfileResponseDTO> getProfile(Authentication authentication) {
        return ResponseEntity.ok(profileService.getProfile(authentication.getName()));
    }

    @PostMapping("/profile")
    @Operation(summary = "Crea un nuovo profilo",
               description = "Crea il profilo per l'utente loggato.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profilo creato"),
        @ApiResponse(responseCode = "400", description = "Dati non validi"),
        @ApiResponse(responseCode = "409", description = "Profilo già esistente"),
        @ApiResponse(responseCode = "401", description = "Non autenticato")
    })
    public ResponseEntity<ProfileResponseDTO> createProfile(
            Authentication authentication,
            @Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.ok(profileService.createProfile(authentication.getName(), dto));
    }

    @PutMapping("/profile")
    @Operation(summary = "Modifica il tuo profilo",
               description = "Aggiorna i dati del tuo profilo personale.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profilo aggiornato"),
        @ApiResponse(responseCode = "400", description = "Dati non validi"),
        @ApiResponse(responseCode = "404", description = "Profilo non trovato"),
        @ApiResponse(responseCode = "401", description = "Non autenticato")
    })
    public ResponseEntity<ProfileResponseDTO> updateProfile(
            Authentication authentication,
            @Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.ok(profileService.updateProfile(authentication.getName(), dto));
    }
}