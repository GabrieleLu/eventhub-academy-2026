package com.gabriele.eventhub.service;

import com.gabriele.eventhub.dto.SignupRequestDTO;
import com.gabriele.eventhub.entity.Role;
import com.gabriele.eventhub.entity.User;
import com.gabriele.eventhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthService {
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
	    this.userRepository = userRepository;
	    this.passwordEncoder = passwordEncoder;
	}

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email già in uso");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);
        user.setEnabled(true);

        userRepository.save(user);
    }
}