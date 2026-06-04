package com.gabriele.eventhub.service;

import com.gabriele.eventhub.dto.VenueRequestDTO;
import com.gabriele.eventhub.dto.VenueResponseDTO;
import com.gabriele.eventhub.entity.Venue;
import com.gabriele.eventhub.exception.ResourceNotFoundException;
import com.gabriele.eventhub.repository.VenueRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<VenueResponseDTO> findAll() {
        List<Venue> venues = venueRepository.findAll();
        List<VenueResponseDTO> result = new ArrayList<>();
        for (Venue venue : venues) {
            result.add(toDTO(venue));
        }
        return result;
    }

    public VenueResponseDTO findById(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sede non trovata"));
        return toDTO(venue);
    }

    public VenueResponseDTO create(VenueRequestDTO dto) {
        Venue venue = new Venue();
        venue.setName(dto.getName());
        venue.setAddress(dto.getAddress());
        venue.setCity(dto.getCity());
        venue.setCapacity(dto.getCapacity());

        venueRepository.save(venue);
        return toDTO(venue);
    }

    public VenueResponseDTO update(Long id, VenueRequestDTO dto) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sede non trovata"));

        if (dto.getName() != null) venue.setName(dto.getName());
        if (dto.getAddress() != null) venue.setAddress(dto.getAddress());
        if (dto.getCity() != null) venue.setCity(dto.getCity());
        if (dto.getCapacity() > 0) venue.setCapacity(dto.getCapacity());

        venueRepository.save(venue);
        return toDTO(venue);
    }

    public void delete(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sede non trovata"));
        venueRepository.deleteById(venue.getId());
    }

    private VenueResponseDTO toDTO(Venue venue) {
        VenueResponseDTO dto = new VenueResponseDTO();
        dto.setId(venue.getId());
        dto.setName(venue.getName());
        dto.setAddress(venue.getAddress());
        dto.setCity(venue.getCity());
        dto.setCapacity(venue.getCapacity());
        return dto;
    }
}