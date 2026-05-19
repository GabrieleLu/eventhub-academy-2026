package com.gabriele.eventhub.dto;

import com.gabriele.eventhub.entity.Role;
import jakarta.validation.constraints.Email;

public class UserUpdateDTO {
	
	/*
	 * CLASSE CHE L'ADMIN MANDA PER POTER MODIFICARE UN UTENTE, PUò MODIFICARE
	 * EMAIO, RUOLO O BANNARE E O RENDERE STTIVO LO STATO DELL'UTENTE.
	 * 
	 */

    @Email(message = "Formato email non valido")
    private String email;

    private Role role;

    private Boolean enabled;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Boolean isEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
}