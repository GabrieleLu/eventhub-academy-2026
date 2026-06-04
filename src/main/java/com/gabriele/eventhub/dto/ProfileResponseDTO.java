package com.gabriele.eventhub.dto;

public class ProfileResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String bio;
    private String city;
    private String photoUrl;
    private String email;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}