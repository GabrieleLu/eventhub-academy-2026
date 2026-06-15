package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.UserResponseDTO;
import com.gabriele.eventhub.dto.UserUpdateDTO;
import com.gabriele.eventhub.service.UserService;
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
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Gestione amministrativa degli utenti (solo ADMIN)")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @Operation(summary = "Recupera tutti gli utenti",
               description = "Restituisce lista di tutti gli utenti registrati. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista di utenti recuperata"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato (non ADMIN)")
    })
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Recupera un utente per ID",
               description = "Restituisce i dettagli di uno specifico utente. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Utente trovato"),
        @ApiResponse(responseCode = "404", description = "Utente non trovato"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<UserResponseDTO> findById(@Parameter(description = "ID dell'utente") @PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Modifica un utente",
               description = "Aggiorna i dati di un utente. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Utente modificato"),
        @ApiResponse(responseCode = "404", description = "Utente non trovato"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<UserResponseDTO> update(
            @Parameter(description = "ID dell'utente") @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Elimina un utente",
               description = "Rimuove un utente dal sistema. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Utente eliminato"),
        @ApiResponse(responseCode = "404", description = "Utente non trovato"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID dell'utente") @PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/role")
    @Operation(summary = "Aggiorna il ruolo di un utente",
               description = "Cambia il ruolo (ADMIN, ORGANIZER, USER) di un utente. Solo ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ruolo aggiornato"),
        @ApiResponse(responseCode = "404", description = "Utente non trovato"),
        @ApiResponse(responseCode = "403", description = "Non autorizzato")
    })
    public ResponseEntity<UserResponseDTO> updateRole(
            @Parameter(description = "ID dell'utente") @PathVariable Long id,
            @RequestBody UserUpdateDTO dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }
}