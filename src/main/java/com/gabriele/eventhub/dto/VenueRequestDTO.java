package com.gabriele.eventhub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VenueRequestDTO {

  
    @NotBlank(message = "Nome sede obbligatorio")
    @Size(min = 2, max = 100, message = "Nome deve essere tra 2 e 100 caratteri")
    private String name;

    @NotBlank(message = "Indirizzo obbligatorio")
    @Size(min = 5, max = 200, message = "Indirizzo deve essere tra 5 e 200 caratteri")
    private String address;

    @NotBlank(message = "Città obbligatoria")
    @Size(min = 2, max = 50, message = "Città deve essere tra 2 e 50 caratteri")
    private String city;

    @Min(value = 1, message = "La capienza deve essere almeno 1")
    private int capacity;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}