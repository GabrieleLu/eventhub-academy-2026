package com.gabriele.eventhub.service;

import com.gabriele.eventhub.dto.SpeakerRequestDTO;
import com.gabriele.eventhub.dto.SpeakerResponseDTO;
import com.gabriele.eventhub.entity.Speaker;
import com.gabriele.eventhub.exception.ResourceNotFoundException;
import com.gabriele.eventhub.repository.SpeakerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpeakerService {

    private final SpeakerRepository speakerRepository;

    public SpeakerService(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    public List<SpeakerResponseDTO> findAll() {
        List<Speaker> speakers = speakerRepository.findAll();
        List<SpeakerResponseDTO> result = new ArrayList<>();
        for (Speaker speaker : speakers) {
            result.add(toDTO(speaker));
        }
        return result;
    }

    public SpeakerResponseDTO findById(Long id) {
        Speaker speaker = speakerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Speaker non trovato"));
        return toDTO(speaker);
    }

    public SpeakerResponseDTO create(SpeakerRequestDTO dto) {
        Speaker speaker = new Speaker();
        speaker.setFirstName(dto.getFirstName());
        speaker.setLastName(dto.getLastName());
        speaker.setBio(dto.getBio());
        speaker.setPhotoUrl(dto.getPhotoUrl());

        speakerRepository.save(speaker);
        return toDTO(speaker);
    }

    public SpeakerResponseDTO update(Long id, SpeakerRequestDTO dto) {
        Speaker speaker = speakerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Speaker non trovato"));

        if (dto.getFirstName() != null) speaker.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) speaker.setLastName(dto.getLastName());
        if (dto.getBio() != null) speaker.setBio(dto.getBio());
        if (dto.getPhotoUrl() != null) speaker.setPhotoUrl(dto.getPhotoUrl());

        speakerRepository.save(speaker);
        return toDTO(speaker);
    }

    public void delete(Long id) {
        Speaker speaker = speakerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Speaker non trovato"));
        speakerRepository.deleteById(speaker.getId());
    }

    private SpeakerResponseDTO toDTO(Speaker speaker) {
        SpeakerResponseDTO dto = new SpeakerResponseDTO();
        dto.setId(speaker.getId());
        dto.setFirstName(speaker.getFirstName());
        dto.setLastName(speaker.getLastName());
        dto.setBio(speaker.getBio());
        dto.setPhotoUrl(speaker.getPhotoUrl());
        return dto;
    }
}