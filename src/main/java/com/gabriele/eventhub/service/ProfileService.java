package com.gabriele.eventhub.service;

import com.gabriele.eventhub.dto.ProfileRequestDTO;
import com.gabriele.eventhub.dto.ProfileResponseDTO;
import com.gabriele.eventhub.entity.Profile;
import com.gabriele.eventhub.entity.User;
import com.gabriele.eventhub.exception.ResourceNotFoundException;
import com.gabriele.eventhub.exception.ValidationException;
import com.gabriele.eventhub.repository.ProfileRepository;
import com.gabriele.eventhub.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public ProfileResponseDTO getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Profilo non trovato"));
        return toDTO(profile);
    }

    public ProfileResponseDTO createProfile(String email, ProfileRequestDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));

        if (profileRepository.findByUserId(user.getId()).isPresent()) {
            throw new ValidationException("Profilo già esistente");
        }

        Profile profile = new Profile();
        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        profile.setBio(dto.getBio());
        profile.setCity(dto.getCity());
        profile.setPhotoUrl(dto.getPhotoUrl());
        profile.setUser(user);

        profileRepository.save(profile);
        return toDTO(profile);
    }

    public ProfileResponseDTO updateProfile(String email, ProfileRequestDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Profilo non trovato"));

        if (dto.getFirstName() != null) profile.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) profile.setLastName(dto.getLastName());
        if (dto.getBio() != null) profile.setBio(dto.getBio());
        if (dto.getCity() != null) profile.setCity(dto.getCity());
        if (dto.getPhotoUrl() != null) profile.setPhotoUrl(dto.getPhotoUrl());

        profileRepository.save(profile);
        return toDTO(profile);
    }

    private ProfileResponseDTO toDTO(Profile profile) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(profile.getId());
        dto.setFirstName(profile.getFirstName());
        dto.setLastName(profile.getLastName());
        dto.setBio(profile.getBio());
        dto.setCity(profile.getCity());
        dto.setPhotoUrl(profile.getPhotoUrl());
        dto.setEmail(profile.getUser().getEmail());
        return dto;
    }
}