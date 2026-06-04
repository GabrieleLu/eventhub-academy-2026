package com.gabriele.eventhub.dto;

import com.gabriele.eventhub.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserUpdateDTO {
	
	/*
	 * CLASSE CHE L'ADMIN MANDA PER POTER MODIFICARE UN UTENTE, PUò MODIFICARE
	 * EMAIO, RUOLO O BANNARE E O RENDERE STTIVO LO STATO DELL'UTENTE.
	 * 
	 */

	@Email(message = "Formato email non valido")
	@Size(min = 5, max = 100, message = "Email deve essere tra 5 e 100 caratteri")
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