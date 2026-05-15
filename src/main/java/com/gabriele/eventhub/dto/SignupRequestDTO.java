package com.gabriele.eventhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequestDTO {

    @NotBlank(message = "Email obbligatoria")
    @Email(message = "Formato email non valido")
    private String email;

    @NotBlank(message = "Password obbligatoria")
    @Size(min = 6, message = "La password deve avere almeno 6 caratteri")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}