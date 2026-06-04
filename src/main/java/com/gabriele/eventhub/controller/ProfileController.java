package com.gabriele.eventhub.controller;

import com.gabriele.eventhub.dto.ProfileRequestDTO;
import com.gabriele.eventhub.dto.ProfileResponseDTO;
import com.gabriele.eventhub.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/me")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> getProfile(Authentication authentication) {
        return ResponseEntity.ok(profileService.getProfile(authentication.getName()));
    }

    @PostMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> createProfile(
            Authentication authentication,
            @Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.ok(profileService.createProfile(authentication.getName(), dto));
    }

    @PutMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> updateProfile(
            Authentication authentication,
            @Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.ok(profileService.updateProfile(authentication.getName(), dto));
    }
}