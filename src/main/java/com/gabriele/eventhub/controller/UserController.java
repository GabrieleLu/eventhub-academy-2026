package com.gabriele.eventhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "User Info", description = "Informazioni generali dell'utente autenticato")
public class UserController {

    @GetMapping("/me")
    @Operation(summary = "Recupera l'email dell'utente loggato",
               description = "Restituisce l'email dell'utente attualmente autenticato.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Email dell'utente restituita"),
        @ApiResponse(responseCode = "401", description = "Non autenticato")
    })
    public ResponseEntity<String> me(Authentication authentication) {
        return ResponseEntity.ok("Utente autenticato: " + authentication.getName());
    }
}