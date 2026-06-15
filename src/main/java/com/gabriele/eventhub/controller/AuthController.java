package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.SignupRequestDTO;
import com.gabriele.eventhub.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Autenticazione e registrazione")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    @Operation(summary = "Registra un nuovo utente",
               description = "Crea un nuovo account con email e password. Richiede email unica e password valida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registrazione avvenuta con successo"),
        @ApiResponse(responseCode = "400", description = "Dati non validi"),
        @ApiResponse(responseCode = "409", description = "Email già registrata")
    })
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDTO dto) {
        authService.signup(dto);
        return ResponseEntity.ok("Registrazione avvenuta con successo");
    }
}