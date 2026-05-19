package com.gabriele.eventhub.service;

import com.gabriele.eventhub.dto.UserResponseDTO;
import com.gabriele.eventhub.dto.UserUpdateDTO;
import com.gabriele.eventhub.entity.User;
import com.gabriele.eventhub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
	
	/*
	 * 
	 *    CRUD UTENTI  ESEGUITO DA ADMIN
	 * LISTA TUTTI UTENTI
	 * DETTAGLIO UTENTE
	 * MODIFICARE UTENTE
	 * CANCELLARE UTENTE
	 * 
	 * 
	 * 
	 */

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> result = new ArrayList<>();
        for (User user : users) {
            result.add(toDTO(user));
        }
        return result;
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return toDTO(user);
    }

    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }
        if (dto.isEnabled() != null) {
            user.setEnabled(dto.isEnabled());
        }

        userRepository.save(user);
        return toDTO(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        userRepository.deleteById(user.getId());
    }

    private UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setEnabled(user.isEnabled());
        return dto;
    }
}