package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.SignupRequestDTO;
import com.gabriele.eventhub.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {
	public AuthController(AuthService authService) {
	    this.authService = authService;
	}

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDTO dto) {
        authService.signup(dto);
        return ResponseEntity.ok("Registrazione avvenuta con successo");
    }

}