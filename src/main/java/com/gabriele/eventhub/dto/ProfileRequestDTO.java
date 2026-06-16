package com.gabriele.eventhub.dto;


import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;


public class ProfileRequestDTO {

    @NotBlank(message = "Nome obbligatorio")
    @Size(min = 1, max = 50, message = "Nome deve essere tra 1 e 50 caratteri")
    private String firstName;

    @NotBlank(message = "Cognome obbligatorio")
    @Size(min = 1, max = 50, message = "Cognome deve essere tra 1 e 50 caratteri")
    private String lastName;

    @Size(max = 500, message = "Bio può contenere max 500 caratteri")
    private String bio;

    
    private String city;
    
    @URL(message = "URL foto non valido")
    private String photoUrl;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
}