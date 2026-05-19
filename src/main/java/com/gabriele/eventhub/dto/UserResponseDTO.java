package com.gabriele.eventhub.dto;

import com.gabriele.eventhub.entity.Role;

public class UserResponseDTO {
	
	/*
	 *  E LA CLASSE CHE RESTITUISCE I DATI DI UN UTENTE ALL'ADMIN, NON CONTIENE PASSWORD
	 * 
	 * 
	 * 
	 */

    private Long id;
    private String email;
    private Role role;
    private boolean enabled;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}